package com.scouter.jsonblockpatterns.registry;

import com.scouter.jsonblockpatterns.JsonBlockPatterns;
import com.scouter.jsonblockpatterns.data.BlockPatternBuilderType;
import com.scouter.jsonblockpatterns.data.KeyDefinition;
import com.scouter.jsonblockpatterns.data.KeyDefinitionType;
import com.scouter.jsonblockpatterns.data.NormalBlockPatternBuilder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class KeyDefinitionRegistry {
    public static final DeferredRegister<KeyDefinitionType<?>> KEY_DEFINITION_SERIALIZER = DeferredRegister.create(JBPRegistries.Keys.KEY_DEFINITION_TYPE_SERIALIZER, JsonBlockPatterns.MODID);
    public static final DeferredHolder<KeyDefinitionType<?>, KeyDefinitionType<KeyDefinition>> KEY = KEY_DEFINITION_SERIALIZER.register("key", () -> KeyDefinition.TYPE);
}
