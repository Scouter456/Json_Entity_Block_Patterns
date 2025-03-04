package com.scouter.jsonblockpatterns.data;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface KeyDefinitionType <T extends KeyDefinitionCodec> {

    MapCodec<T> mapCodec();

    StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();

}
