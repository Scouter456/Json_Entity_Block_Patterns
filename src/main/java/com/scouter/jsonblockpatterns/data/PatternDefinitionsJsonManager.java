package com.scouter.jsonblockpatterns.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternDefinitionsJsonManager extends SimpleJsonResourceReloadListener {

    private static final Gson STANDARD_GSON = new Gson();
    public static final Logger LOGGER = LogManager.getLogger();
    private final String folderName;

    protected static Map<ResourceLocation, BlockPatternBuilderCodec> blockPatternEntries = new HashMap<>();
    protected static Map<Block, List<BlockPatternBuilderCodec>> headBlockPatterns = new HashMap<>();

    public PatternDefinitionsJsonManager() {
        this("pattern_definitions/pattern", STANDARD_GSON);
    }

    public static Map<ResourceLocation, BlockPatternBuilderCodec> getBlockPatternEntries() {
        return blockPatternEntries;
    }

    public static void populeteHeadBlockMap(ServerLevel level) {
        if(headBlockPatterns.isEmpty()) {
            for (BlockPatternBuilderCodec blockPatternDefinition : getBlockPatternEntries().values()) {
                List<Block> blockList = blockPatternDefinition.getHead().getAllBlocks(level);
                for (Block block : blockList) {
                    List<BlockPatternBuilderCodec> patL = headBlockPatterns.computeIfAbsent(block, (e) -> new ArrayList<>());
                    patL.add(blockPatternDefinition);
                    headBlockPatterns.put(block, patL);
                }
            }
            LOGGER.info("Created {} head definitions", headBlockPatterns.keySet().size());
        }
    }

    public static List<BlockPatternBuilderCodec> getPatternsForBlock(Block block, Level level) {
        if(headBlockPatterns.isEmpty())  {
            if(!level.isClientSide) {
                populeteHeadBlockMap((ServerLevel) level);
            }
        }

        return headBlockPatterns.getOrDefault(block, new ArrayList<>());
    }


    public PatternDefinitionsJsonManager(String folderName, Gson gson) {
        super(gson, folderName);
        this.folderName = folderName;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        this.blockPatternEntries.clear();
        this.headBlockPatterns.clear();
        Map<ResourceLocation, BlockPatternBuilderCodec> blockPatternEntries = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsons.entrySet()) {
            ResourceLocation key = entry.getKey();
            JsonElement element = entry.getValue();
            BlockPatternBuilderCodec.DIRECT_CODEC.decode(JsonOps.INSTANCE, element)
                    .ifSuccess(result -> {
                        BlockPatternBuilderCodec codec = result.getFirst();
                        blockPatternEntries.put(key, codec);
                    })
                    .ifError(partial -> LOGGER.error("Failed to parse pattern JSON for {} due to: {}", key, partial.message()));


        }

        this.blockPatternEntries = blockPatternEntries;
        LOGGER.info("Data loader for {} loaded {} jsons", this.folderName, this.blockPatternEntries.size());
    }
}