package com.scouter.jsonblockpatterns.registry;

import com.scouter.jsonblockpatterns.data.BlockPatternBuilderType;
import com.scouter.jsonblockpatterns.data.NormalBlockPatternBuilder;
import net.minecraft.core.Registry;

import static com.scouter.jsonblockpatterns.JsonBlockPatterns.prefix;

public class BlockPatternBuilderRegistry {
    public static final BlockPatternBuilderType<?> NORMAL_PATTERN = registerBlockPatternBuilder("normal", NormalBlockPatternBuilder.TYPE);

    private static BlockPatternBuilderType<?> registerBlockPatternBuilder(String name, BlockPatternBuilderType<?> type) {
        return Registry.register(JBPRegistries.BLOCK_PATTERN_BUILDER_TYPE_SERIALIZER, prefix(name), type);
    }
    public static void register()
    {
    }
}
