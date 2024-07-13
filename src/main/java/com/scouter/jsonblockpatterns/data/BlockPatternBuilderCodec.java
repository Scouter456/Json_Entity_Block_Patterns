package com.scouter.jsonblockpatterns.data;

import com.mojang.serialization.Codec;
import com.scouter.jsonblockpatterns.registry.JBPRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

import java.util.List;

public interface BlockPatternBuilderCodec {

    Codec<BlockPatternBuilderCodec> DIRECT_CODEC = JBPRegistries.BLOCK_PATTERN_BUILDER_TYPE_SERIALIZER.byNameCodec()
            .dispatch(BlockPatternBuilderCodec::type, BlockPatternBuilderType::mapCodec);

    StreamCodec<RegistryFriendlyByteBuf, BlockPatternBuilderCodec> DIRECT_STREAM_CODEC =
            ByteBufCodecs.registry(JBPRegistries.BLOCK_PATTERN_BUILDER_TYPE_SERIALIZER.key())
                    .dispatch(BlockPatternBuilderCodec::type, BlockPatternBuilderType::streamCodec);


    BlockPattern getBlockPattern();

    KeyDefinitionCodec getHead();
    List<KeyDefinitionCodec> getBase();
    EntityType<?> getEntity();


    BlockPatternBuilderType<? extends BlockPatternBuilderCodec> type();


}
