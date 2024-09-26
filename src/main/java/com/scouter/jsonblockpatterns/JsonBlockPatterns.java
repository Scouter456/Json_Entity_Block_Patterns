package com.scouter.jsonblockpatterns;

import com.scouter.jsonblockpatterns.data.BlockPatternBuilderCodec;
import com.scouter.jsonblockpatterns.data.PatternDefinitionsJsonManager;
import com.scouter.jsonblockpatterns.setup.Registration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AdventureModePredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

public class JsonBlockPatterns implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("jsonblockpatterns");
    public static final String MODID = "jsonblockpatterns";

    @Override
    public void onInitialize() {
        Registration.init();
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new PatternDefinitionsJsonManager());
    }


    public static InteractionResult onUseItemInWorld(UseOnContext context) {
        ItemStack itemstack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player != null && !player.getAbilities().mayBuild) {
            AdventureModePredicate adventureModePredicate = itemstack.get(DataComponents.CAN_PLACE_ON);
            if (adventureModePredicate == null || !adventureModePredicate.test(new BlockInWorld(level, context.getClickedPos(), false))) {
                return InteractionResult.PASS;
            }
        }
        Item item = itemstack.getItem();
        int size = itemstack.getCount();
        ItemStack copy = itemstack.copy();
        InteractionResult ret = itemstack.getItem().useOn(context);
        if (ret.consumesAction()) {
            BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
            BlockState state = level.getBlockState(pos);
            if(checkPatterns(state, level, pos)) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static boolean checkPatterns(BlockState pState, Level pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide) {
            List<BlockPatternBuilderCodec> patterns = PatternDefinitionsJsonManager.getPatternsForBlock(pState.getBlock(), pLevel);
            for (BlockPatternBuilderCodec patternBuilderCodec : patterns) {
                BlockPattern.BlockPatternMatch patternMatch = patternBuilderCodec.getBlockPattern().find(pLevel, pPos);
                if (patternMatch != null) {
                    Entity entity = patternBuilderCodec.getEntity().create(pLevel);
                    spawnEntityInWorld(pLevel, patternMatch, entity, pPos);
                    return true;
                }
            }
        }
        return false;
    }


    public static void spawnEntityInWorld(Level level, BlockPattern.BlockPatternMatch patternMatch, Entity entity, BlockPos pos) {
        CarvedPumpkinBlock.clearPatternBlocks(level, patternMatch);
        entity.moveTo((double) pos.getX() + 0.5D, (double) pos.getY() + 0.05D, (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        level.addFreshEntity(entity);
        for (ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(5.0D))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, entity);
        }
        CarvedPumpkinBlock.updatePatternBlocks(level, patternMatch);
        CarvedPumpkinBlock.updatePatternBlocks(level, patternMatch);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}