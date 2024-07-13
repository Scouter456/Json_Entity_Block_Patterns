package com.scouter.jsonblockpatterns.registry;

import com.scouter.jsonblockpatterns.data.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

import static com.scouter.jsonblockpatterns.JsonBlockPatterns.prefix;

public class JBPRegistries {
    static { init(); }
    //public static final Registry<MapCodec<? extends AnimationBuilderCodec>> ANIMATION_BUILDER_SERIALIZER = new RegistryBuilder<>(Keys.ANIMATION_BUILDER_SERIALIZERS).maxId(Integer.MAX_VALUE - 1).sync(false).create();
    public static final Registry<BlockPatternBuilderType<?>> BLOCK_PATTERN_BUILDER_TYPE_SERIALIZER = new RegistryBuilder<>(Keys.BLOCK_PATTERN_BUILDER_TYPE_SERIALIZERS).maxId(Integer.MAX_VALUE - 1).sync(true).create();

    public static final Registry<BlockPatternType<?>> BLOCK_PATTERN_TYPE_SERIALIZER = new RegistryBuilder<>(Keys.BLOCK_PATTERN_TYPE_SERIALIZERS).maxId(Integer.MAX_VALUE - 1).sync(true).create();

    public static final Registry<KeyDefinitionType<?>> KEY_DEFINITION_TYPE_SERIALIZER = new RegistryBuilder<>(Keys.KEY_DEFINITION_TYPE_SERIALIZER).maxId(Integer.MAX_VALUE - 1).sync(false).create();

    public static final class Keys {

        //public static final ResourceKey<Registry<MapCodec<? extends AnimationBuilderCodec>>> ANIMATION_BUILDER_SERIALIZERS = key(prefix("animation_builder_serializer").toString());
        public static final ResourceKey<Registry<BlockPatternBuilderType<?>>> BLOCK_PATTERN_BUILDER_TYPE_SERIALIZERS = key(prefix("block_pattern_builder_type_serializer").toString());
        public static final ResourceKey<Registry<BlockPatternBuilderCodec>> BLOCK_PATTERN_BUILDER_TYPE = key(prefix("block_pattern_builder_type").toString());

        public static final ResourceKey<Registry<BlockPatternType<?>>> BLOCK_PATTERN_TYPE_SERIALIZERS = key(prefix("block_pattern_type_serializer").toString());
        public static final ResourceKey<Registry<BlockPatternCodec>> BLOCK_PATTERN_TYPE = key(prefix("block_pattern_type").toString());

        public static final ResourceKey<Registry<KeyDefinitionType<?>>> KEY_DEFINITION_TYPE_SERIALIZER = key(prefix("key_definition_type_serializer").toString());
        public static final ResourceKey<Registry<KeyDefinitionCodec>> KEY_DEFINITION_TYPE= key(prefix("key_definition_type").toString());

        private static <T> ResourceKey<Registry<T>> key(String name)
        {
            return ResourceKey.createRegistryKey(ResourceLocation.parse(name));
        }
        private static void init() {}

    }

    private static void init()
    {
        Keys.init();
    }
}