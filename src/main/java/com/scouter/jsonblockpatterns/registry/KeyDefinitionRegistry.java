package com.scouter.jsonblockpatterns.registry;

import com.scouter.jsonblockpatterns.data.KeyDefinition;
import com.scouter.jsonblockpatterns.data.KeyDefinitionType;
import net.minecraft.core.Registry;

import static com.scouter.jsonblockpatterns.JsonBlockPatterns.prefix;

public class KeyDefinitionRegistry {

    public static final KeyDefinitionType<?> KEY = registerKeyDefinition("key",  KeyDefinition.TYPE);

    private static KeyDefinitionType<?> registerKeyDefinition(String name, KeyDefinitionType<?> type) {
        return Registry.register(JBPRegistries.KEY_DEFINITION_TYPE_SERIALIZER, prefix(name), type);
    }
    public static void register()
    {
    }

}
