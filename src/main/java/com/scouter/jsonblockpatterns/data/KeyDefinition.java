package com.scouter.jsonblockpatterns.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.scouter.jsonblockpatterns.data.codec.NullableFieldCodec;
import com.scouter.jsonblockpatterns.registry.KeyDefinitionRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KeyDefinition implements KeyDefinitionCodec {

    public static final MapCodec<KeyDefinition> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("char").forGetter(KeyDefinition::getKeyParse),
                    NullableFieldCodec.makeOptionalField("block", BuiltInRegistries.BLOCK.byNameCodec()).forGetter(KeyDefinition::getBlockParse),
                    NullableFieldCodec.makeOptionalField("tag", TagKey.codec(Registries.BLOCK)).forGetter(KeyDefinition::getTagParse)
            ).apply(instance, KeyDefinition::decode)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, KeyDefinition> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, KeyDefinition::getKeyParse,
            ByteBufCodecs.optional(ByteBufCodecs.fromCodec(BuiltInRegistries.BLOCK.byNameCodec())), KeyDefinition::getBlockParse,
            ByteBufCodecs.optional(ByteBufCodecs.fromCodec(TagKey.codec(Registries.BLOCK))), KeyDefinition::getTagParse,
            KeyDefinition::decode
    );

    public static final KeyDefinitionType<KeyDefinition> TYPE = new KeyDefinitionType<KeyDefinition>() {
        @Override
        public MapCodec<KeyDefinition> mapCodec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, KeyDefinition> streamCodec() {
            return STREAM_CODEC;
        }
    };


    private final char key;
    private final String keyString;
    private final Block block;
    private final TagKey<Block> blockTagKey;

    private final Optional<Block> optionalBlock;
    private final Optional<TagKey<Block>> optionalBlockTagKey;
    private List<Block> blockList = new ArrayList<>();

    public static KeyDefinition ofBlock(String key, Block block) {
        return new KeyDefinition(key, Optional.of(block), Optional.empty());
    }

    public static KeyDefinition ofTag(String key, TagKey<Block> blockTagKey) {
        return new KeyDefinition(key, Optional.empty(), Optional.of(blockTagKey));
    }


    public KeyDefinition(String key, Optional<Block> optionalBlock, Optional<TagKey<Block>> optionalBlockTagKey) {
        this.key = key.charAt(0);
        this.keyString = key;
        this.optionalBlock = optionalBlock;
        this.optionalBlockTagKey = optionalBlockTagKey;
        this.block = optionalBlock.orElse(null);
        this.blockTagKey = optionalBlockTagKey.orElse(null);
    }

    public static KeyDefinition decode(String key, Optional<Block> blockOpt, Optional<TagKey<Block>> tagOpt) {
        if (blockOpt.isPresent() && tagOpt.isPresent()) {
            throw new IllegalArgumentException("Cannot have both block and tag defined");
        }

        int length = key.length();
        if (length > 1) throw new IllegalArgumentException("Cannot have a key bigger than 1");
        return new KeyDefinition(key, blockOpt, tagOpt);
    }


    public String getKeyParse() {
        return keyString;
    }

    public Optional<Block> getBlockParse() {
        return optionalBlock;
    }

    public Optional<TagKey<Block>> getTagParse() {
        return optionalBlockTagKey;
    }

    @Override
    public char getKey() {
        return key;
    }

    @Nullable
    @Override
    public Block getBlock() {
        return block;
    }

    @Nullable
    @Override
    public TagKey<Block> getTag() {
        return blockTagKey;
    }

    @Override
    public List<Block> getAllBlocks(ServerLevel level) {
        if (blockTagKey != null && blockList.isEmpty()) {
            level.registryAccess().registry(Registries.BLOCK).ifPresent(reg -> {
                        Iterable<Holder<Block>> holders = reg.getTagOrEmpty(blockTagKey);
                        for (Holder<Block> blockHolder : holders) {
                            if (!blockList.contains(blockHolder.value())) {
                                blockList.add(blockHolder.value());
                            }
                        }
                    }
            );
        } else if(blockList.isEmpty()){
            blockList.add(block);
        }
        return blockList;
    }

    @Override
    public KeyDefinitionType<? extends KeyDefinitionCodec> type() {
        return KeyDefinitionRegistry.KEY;
    }
}
