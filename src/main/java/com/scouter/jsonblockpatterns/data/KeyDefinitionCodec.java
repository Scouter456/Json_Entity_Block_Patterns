package com.scouter.jsonblockpatterns.data;

import com.mojang.serialization.Codec;
import com.scouter.jsonblockpatterns.registry.JBPRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface KeyDefinitionCodec {

    Codec<KeyDefinitionCodec> DIRECT_CODEC = JBPRegistries.KEY_DEFINITION_TYPE_SERIALIZER.byNameCodec()
            .dispatch(KeyDefinitionCodec::type, KeyDefinitionType::mapCodec);

    StreamCodec<RegistryFriendlyByteBuf, KeyDefinitionCodec> DIRECT_STREAM_CODEC =
            ByteBufCodecs.registry(JBPRegistries.KEY_DEFINITION_TYPE_SERIALIZER.key())
                    .dispatch(KeyDefinitionCodec::type, KeyDefinitionType::streamCodec);



    char getKey();

    @Nullable
    Block getBlock();

    @Nullable
    TagKey<Block> getTag();

    List<Block> getAllBlocks(ServerLevel level);

    KeyDefinitionType<? extends KeyDefinitionCodec> type();

}
