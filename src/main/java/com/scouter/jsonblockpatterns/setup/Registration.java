package com.scouter.jsonblockpatterns.setup;

import com.mojang.logging.LogUtils;
import com.scouter.jsonblockpatterns.registry.BlockPatternBuilderRegistry;
import com.scouter.jsonblockpatterns.registry.BlockPatternRegistry;
import com.scouter.jsonblockpatterns.registry.KeyDefinitionRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import org.slf4j.Logger;


public class Registration {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void init(){

        IEventBus bus = ModLoadingContext.get().getActiveContainer().getEventBus();

        BlockPatternBuilderRegistry.BLOCK_PATTERN_SERIALIZER.register(bus);
        BlockPatternRegistry.BLOCK_DEFINITION_SERIALIZER.register(bus);
        KeyDefinitionRegistry.KEY_DEFINITION_SERIALIZER.register(bus);

        //PMAnimationBuilderRegistry.ANIMATION_TYPE_SERIALIZER.register(bus);
        //PMEntityTypesRegistry.ENTITY_TYPE_SERIALIZER.register(bus);


    }
}
