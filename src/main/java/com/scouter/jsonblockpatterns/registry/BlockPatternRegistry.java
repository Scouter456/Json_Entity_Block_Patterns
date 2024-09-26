package com.scouter.jsonblockpatterns.registry;

import com.scouter.jsonblockpatterns.data.BlockPatternDefinition;
import com.scouter.jsonblockpatterns.data.BlockPatternType;
import net.minecraft.core.Registry;

import static com.scouter.jsonblockpatterns.JsonBlockPatterns.prefix;

public class BlockPatternRegistry {

    public static final BlockPatternType<?> PATTERN = registerBlockPatternDefinition("pattern", BlockPatternDefinition.TYPE);

    private static BlockPatternType<?> registerBlockPatternDefinition(String name, BlockPatternType<?> type) {
        return Registry.register(JBPRegistries.BLOCK_PATTERN_TYPE_SERIALIZER, prefix(name), type);
    }
    public static void register()
    {
    }
}
