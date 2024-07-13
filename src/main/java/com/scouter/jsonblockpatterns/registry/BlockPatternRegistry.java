package com.scouter.jsonblockpatterns.registry;

import com.scouter.jsonblockpatterns.JsonBlockPatterns;
import com.scouter.jsonblockpatterns.data.BlockPatternDefinition;
import com.scouter.jsonblockpatterns.data.BlockPatternType;
import com.scouter.jsonblockpatterns.data.KeyDefinition;
import com.scouter.jsonblockpatterns.data.KeyDefinitionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockPatternRegistry {
    public static final DeferredRegister<BlockPatternType<?>> BLOCK_DEFINITION_SERIALIZER = DeferredRegister.create(JBPRegistries.Keys.BLOCK_PATTERN_TYPE_SERIALIZERS, JsonBlockPatterns.MODID);
    public static final DeferredHolder<BlockPatternType<?>, BlockPatternType<BlockPatternDefinition>> PATTERN = BLOCK_DEFINITION_SERIALIZER.register("pattern", () -> BlockPatternDefinition.TYPE);
}
