package com.scouter.jsonblockpatterns;

import com.mojang.logging.LogUtils;
import com.scouter.jsonblockpatterns.data.BlockPatternBuilderCodec;
import com.scouter.jsonblockpatterns.data.BlockPatternCodec;
import com.scouter.jsonblockpatterns.data.KeyDefinitionCodec;
import com.scouter.jsonblockpatterns.registry.JBPRegistries;
import com.scouter.jsonblockpatterns.setup.ModSetup;
import com.scouter.jsonblockpatterns.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

import java.util.Locale;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(JsonBlockPatterns.MODID)
public class JsonBlockPatterns
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "jsonblockpatterns";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public JsonBlockPatterns(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        Registration.init();
        ModSetup.setup();
        IEventBus forgeBus = NeoForge.EVENT_BUS;
        IEventBus modbus = ModLoadingContext.get().getActiveContainer().getEventBus();
        modbus.addListener(ModSetup::init);
        modbus.addListener((DataPackRegistryEvent.NewRegistry event) -> {
            event.dataPackRegistry(JBPRegistries.Keys.KEY_DEFINITION_TYPE, KeyDefinitionCodec.DIRECT_CODEC);
            event.dataPackRegistry(JBPRegistries.Keys.BLOCK_PATTERN_TYPE, BlockPatternCodec.DIRECT_CODEC);
            event.dataPackRegistry(JBPRegistries.Keys.BLOCK_PATTERN_BUILDER_TYPE, BlockPatternBuilderCodec.DIRECT_CODEC);
        });
    }

    public static ResourceLocation prefix(String name) {
        return  ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}
