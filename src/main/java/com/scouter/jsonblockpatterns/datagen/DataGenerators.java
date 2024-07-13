package com.scouter.jsonblockpatterns.datagen;

import com.scouter.jsonblockpatterns.JsonBlockPatterns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = JsonBlockPatterns.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        BlockTagsGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new BlockTagsGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new PatternGenerator(packOutput));
        generator.addProvider(event.includeServer(), new WikiPageGenerator(packOutput));
    }
}
