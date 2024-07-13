package com.scouter.jsonblockpatterns.datagen;

import com.scouter.jsonblockpatterns.data.BlockPatternBuilderCodec;
import net.minecraft.resources.ResourceLocation;

public class PatternConsumer {

    private ResourceLocation location;
    private BlockPatternBuilderCodec builderCodec;

    public PatternConsumer(ResourceLocation loc, BlockPatternBuilderCodec builderCodec) {
        this.location = loc;
        this.builderCodec = builderCodec;
    }

    // Getter for location
    public ResourceLocation getLocation() {
        return location;
    }

    // Setter for location
    public void setLocation(ResourceLocation location) {
        this.location = location;
    }

    // Getter for puppetData
    public BlockPatternBuilderCodec getBlockPattern() {
        return builderCodec;
    }

}
