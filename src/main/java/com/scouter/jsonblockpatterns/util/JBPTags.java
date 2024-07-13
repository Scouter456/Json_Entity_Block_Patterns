package com.scouter.jsonblockpatterns.util;

import com.scouter.jsonblockpatterns.JsonBlockPatterns;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class JBPTags {

    public static final TagKey<Block> ALL = registerBlockTag("all");
    public static final TagKey<Block> BLACK_LIST = registerBlockTag("blacklist");

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registries.BLOCK,  ResourceLocation.fromNamespaceAndPath(JsonBlockPatterns.MODID, name));
    }
}

