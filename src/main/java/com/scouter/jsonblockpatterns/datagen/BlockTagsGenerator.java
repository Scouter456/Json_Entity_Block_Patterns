package com.scouter.jsonblockpatterns.datagen;

import com.scouter.jsonblockpatterns.JsonBlockPatterns;
import com.scouter.jsonblockpatterns.util.JBPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, JsonBlockPatterns.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        for (Iterator<Block> it = BuiltInRegistries.BLOCK.iterator(); it.hasNext(); ) {
            Block block = it.next();
            if(!block.equals(Blocks.BEDROCK) && !block.equals(Blocks.SPAWNER) && !block.equals(Blocks.CHEST) && !block.equals(Blocks.END_PORTAL_FRAME) && !block.equals(Blocks.REINFORCED_DEEPSLATE) && !block.equals(Blocks.TRIAL_SPAWNER) && !block.equals(Blocks.VAULT)) {
                tag(JBPTags.ALL).add(block);
            }
        }

        tag(JBPTags.BLACK_LIST).addTags(BlockTags.FEATURES_CANNOT_REPLACE);

    }


    @Override
    public String getName() {
        return JsonBlockPatterns.MODID + " Block Tags";
    }
}
