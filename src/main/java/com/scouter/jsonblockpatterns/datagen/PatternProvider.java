package com.scouter.jsonblockpatterns.datagen;

import com.google.common.collect.Sets;
import com.mojang.serialization.JsonOps;
import com.scouter.jsonblockpatterns.data.BlockPatternBuilderCodec;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.scouter.jsonblockpatterns.JsonBlockPatterns.prefix;

public abstract class PatternProvider implements DataProvider {
    protected final PackOutput.PathProvider patternPathProvider;

    public PatternProvider(PackOutput pOutput) {
        this.patternPathProvider = pOutput.createPathProvider(PackOutput.Target.DATA_PACK, prefix("pattern_definitions/pattern").getPath());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        Set<ResourceLocation> set = Sets.newHashSet();
        Set<ResourceLocation> taskSet = Sets.newHashSet();
        List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildPatterns((pattern -> {
            if (!set.add(pattern.getLocation())) {
                throw new IllegalStateException("Duplicate pattern " + pattern.getLocation());
            } else {

                BlockPatternBuilderCodec.DIRECT_CODEC.encodeStart(JsonOps.INSTANCE, pattern.getBlockPattern())
                        .ifError(partial -> LOGGER.error("Failed to create block pattern {}, due to {}", pattern.getLocation(), partial))
                        .ifSuccess(e -> list.add(DataProvider.saveStable(pOutput, e, this.patternPathProvider.json(pattern.getLocation()))));
            }
        }));
        return CompletableFuture.allOf(list.toArray((p_253414_) -> {
            return new CompletableFuture[p_253414_];
        }));
    }


    protected abstract void buildPatterns(Consumer<PatternConsumer> pWriter);

    @Override
    public String getName() {
        return "patterns";
    }
}
