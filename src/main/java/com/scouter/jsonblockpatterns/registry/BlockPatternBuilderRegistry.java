package com.scouter.jsonblockpatterns.registry;

import com.scouter.jsonblockpatterns.JsonBlockPatterns;
import com.scouter.jsonblockpatterns.data.BlockPatternBuilderType;
import com.scouter.jsonblockpatterns.data.KeyDefinitionType;
import com.scouter.jsonblockpatterns.data.NormalBlockPatternBuilder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockPatternBuilderRegistry {
    public static final DeferredRegister<BlockPatternBuilderType<?>> BLOCK_PATTERN_SERIALIZER = DeferredRegister.create(JBPRegistries.Keys.BLOCK_PATTERN_BUILDER_TYPE_SERIALIZERS, JsonBlockPatterns.MODID);
    public static final DeferredHolder<BlockPatternBuilderType<?>, BlockPatternBuilderType<NormalBlockPatternBuilder>> NORMAL_PATTERN = BLOCK_PATTERN_SERIALIZER.register("normal", () -> NormalBlockPatternBuilder.TYPE);
}
