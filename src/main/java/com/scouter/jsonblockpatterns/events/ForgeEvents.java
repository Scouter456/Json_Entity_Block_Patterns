package com.scouter.jsonblockpatterns.events;

import com.mojang.logging.LogUtils;
import com.scouter.jsonblockpatterns.JsonBlockPatterns;
import com.scouter.jsonblockpatterns.data.BlockPatternBuilderCodec;
import com.scouter.jsonblockpatterns.data.PatternDefinitionsJsonManager;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.List;

@EventBusSubscriber(modid = JsonBlockPatterns.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ForgeEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onRegisterReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new PatternDefinitionsJsonManager());
    }

    @SubscribeEvent
    public static void checkPatterns(BlockEvent.EntityPlaceEvent event) {
        if(!event.getEntity().level().isClientSide){
        List<BlockPatternBuilderCodec> patterns = PatternDefinitionsJsonManager.getPatternsForBlock(event.getPlacedBlock().getBlock(), event.getEntity().level());
        for(BlockPatternBuilderCodec patternBuilderCodec : patterns) {
            BlockPattern.BlockPatternMatch patternMatch = patternBuilderCodec.getBlockPattern().find(event.getEntity().level(), event.getPos());
                if(patternMatch != null) {
                Entity entity = patternBuilderCodec.getEntity().create(event.getEntity().level());
                spawnEntityInWorld(event.getEntity().level(), patternMatch, entity, event.getPos());
            }
        }}
    }

    public static void spawnEntityInWorld(Level level, BlockPattern.BlockPatternMatch patternMatch, Entity entity , BlockPos pos) {
        CarvedPumpkinBlock.clearPatternBlocks(level, patternMatch);
        entity.moveTo((double)pos.getX() + 0.5D, (double)pos.getY() + 0.05D, (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
        level.addFreshEntity(entity);
        for(ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(5.0D))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, entity);
        }
        CarvedPumpkinBlock.updatePatternBlocks(level, patternMatch);
    }

    @SubscribeEvent
    public static void serverStart(ServerStartingEvent event) {
        PatternDefinitionsJsonManager.populeteHeadBlockMap(event.getServer().overworld());
    }
}

