package com.scouter.jsonblockpatterns.setup;

import com.mojang.logging.LogUtils;
import com.scouter.jsonblockpatterns.registry.BlockPatternBuilderRegistry;
import com.scouter.jsonblockpatterns.registry.BlockPatternRegistry;
import com.scouter.jsonblockpatterns.registry.KeyDefinitionRegistry;
import org.slf4j.Logger;


public class Registration {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void init(){


        BlockPatternBuilderRegistry.register();
        BlockPatternRegistry.register();
        KeyDefinitionRegistry.register();

        //PMAnimationBuilderRegistry.ANIMATION_TYPE_SERIALIZER.register(bus);
        //PMEntityTypesRegistry.ENTITY_TYPE_SERIALIZER.register(bus);


    }
}
