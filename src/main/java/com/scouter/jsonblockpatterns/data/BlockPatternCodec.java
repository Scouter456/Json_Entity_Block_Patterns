package com.scouter.jsonblockpatterns.data;

import com.mojang.serialization.Codec;
import com.scouter.jsonblockpatterns.registry.JBPRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public interface BlockPatternCodec {

    Codec<BlockPatternCodec> DIRECT_CODEC = JBPRegistries.BLOCK_PATTERN_TYPE_SERIALIZER.byNameCodec()
            .dispatch(BlockPatternCodec::type, BlockPatternType::mapCodec);

    StreamCodec<RegistryFriendlyByteBuf, BlockPatternCodec> DIRECT_STREAM_CODEC =
            ByteBufCodecs.registry(JBPRegistries.BLOCK_PATTERN_TYPE_SERIALIZER.key())
                    .dispatch(BlockPatternCodec::type, BlockPatternType::streamCodec);


    List<String> getPattern();


    BlockPatternType<? extends BlockPatternCodec> type();


}
