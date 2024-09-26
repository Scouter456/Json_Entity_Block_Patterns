package com.scouter.jsonblockpatterns.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.scouter.jsonblockpatterns.registry.BlockPatternRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public class BlockPatternDefinition implements BlockPatternCodec {

    public static final MapCodec<BlockPatternDefinition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("keys").forGetter(BlockPatternDefinition::getPatternParse)
    ).apply(instance, BlockPatternDefinition::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BlockPatternDefinition> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), BlockPatternDefinition::getPatternParse,
            BlockPatternDefinition::new
    );
    public static final BlockPatternType<BlockPatternDefinition> TYPE = new BlockPatternType<BlockPatternDefinition>() {
        @Override
        public MapCodec<BlockPatternDefinition> mapCodec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BlockPatternDefinition> streamCodec() {
            return STREAM_CODEC;
        }
    };

    private final List<String> pattern;

    public BlockPatternDefinition(List<String> pattern) {
        this.pattern = pattern;
    }

    public List<String> getPatternParse() {
        return pattern;
    }

    @Override
    public List<String> getPattern() {
        return pattern;
    }

    @Override
    public BlockPatternType<? extends BlockPatternCodec> type() {
        return BlockPatternRegistry.PATTERN;
    }
}
