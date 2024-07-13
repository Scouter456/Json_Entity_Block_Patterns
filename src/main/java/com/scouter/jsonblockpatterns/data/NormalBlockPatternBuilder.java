package com.scouter.jsonblockpatterns.data;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.scouter.jsonblockpatterns.registry.BlockPatternBuilderRegistry;
import com.scouter.jsonblockpatterns.util.JBPTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NormalBlockPatternBuilder implements BlockPatternBuilderCodec {

    public static final MapCodec<NormalBlockPatternBuilder> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockPatternCodec.DIRECT_CODEC.fieldOf("pattern").forGetter(NormalBlockPatternBuilder::getPattern),
            KeyDefinition.DIRECT_CODEC.fieldOf("head").forGetter(NormalBlockPatternBuilder::getHeadParse),
            KeyDefinition.DIRECT_CODEC.listOf().fieldOf("base").forGetter(NormalBlockPatternBuilder::getBaseParse),
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity").forGetter(NormalBlockPatternBuilder::getEntityType)
    ).apply(instance, NormalBlockPatternBuilder::new));


    public static final StreamCodec<RegistryFriendlyByteBuf, NormalBlockPatternBuilder> STREAM_CODEC = StreamCodec.composite(
            BlockPatternCodec.DIRECT_STREAM_CODEC, NormalBlockPatternBuilder::getPattern,
            KeyDefinition.DIRECT_STREAM_CODEC, NormalBlockPatternBuilder::getHeadParse,
            KeyDefinition.DIRECT_STREAM_CODEC.apply(ByteBufCodecs.list()), NormalBlockPatternBuilder::getBaseParse,
            ByteBufCodecs.fromCodec(BuiltInRegistries.ENTITY_TYPE.byNameCodec()), NormalBlockPatternBuilder::getEntityType,
            NormalBlockPatternBuilder::new
    );
    public static final BlockPatternBuilderType<NormalBlockPatternBuilder> TYPE = new BlockPatternBuilderType<NormalBlockPatternBuilder>() {
        @Override
        public MapCodec<NormalBlockPatternBuilder> mapCodec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, NormalBlockPatternBuilder> streamCodec() {
            return STREAM_CODEC;
        }
    };

    private final List<KeyDefinitionCodec> base;
    private final KeyDefinitionCodec head;
    private final BlockPatternCodec pattern;
    private BlockPattern blockPattern;
    private EntityType<?> entityType;

    public NormalBlockPatternBuilder(BlockPatternCodec patternType, KeyDefinitionCodec head, List<KeyDefinitionCodec> base, EntityType<?> entityType) {
        if (!validatePattern(patternType, head, base)) throw new RuntimeException("Pattern not fully defined!");
        this.base = base;
        this.head = head;
        this.pattern = patternType;
        this.entityType = entityType;
    }

    private boolean validatePattern(BlockPatternCodec patternType, KeyDefinitionCodec head, List<KeyDefinitionCodec> base) {
        List<String> keys = patternType.getPattern();
        if (!areAllStringsSameLength(keys)) {
            throw new IllegalArgumentException("Pattern keys must all be the same length");
        }

        int chars = 0;


        for (String pat : keys) {
            chars += pat.length();
        }

        boolean hasHeadDefined = false;
        boolean hasBaseFullyDefined = false;
        int keyCount = 0;

        for (String pat : keys) {
            for (int i = 0; i < pat.length(); i++) {
                char ch = pat.charAt(i);
                if (ch == head.getKey()) {
                    if (head.getBlock() != null || head.getTag() != null) {
                        keyCount++;
                        hasHeadDefined = true;
                    }
                }
                for (KeyDefinitionCodec baseKeys : base) {
                    if (ch == baseKeys.getKey()) {
                        if (baseKeys.getBlock() != null || baseKeys.getTag() != null) {
                            keyCount++;
                        }
                    }
                }
            }
        }
        if(!hasHeadDefined) throw new RuntimeException("Head for Pattern has not been defined!");
        return keyCount == chars;
    }

    private boolean areAllStringsSameLength(List<String> strings) {
        if (strings.isEmpty()) {
            return true;
        }
        int firstLength = strings.get(0).length();
        for (String str : strings) {
            if (str.length() != firstLength) {
                return false;
            }
        }
        return true;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public BlockPatternCodec getPattern() {
        return pattern;
    }

    public KeyDefinitionCodec getHeadParse() {
        return head;
    }

    public List<KeyDefinitionCodec> getBaseParse() {
        return base;
    }

    //todo add only base for dispenser
    @Override
    public BlockPattern getBlockPattern() {
        if (this.blockPattern == null) {
            Set<Character> definedChars = new HashSet<>();
            BlockPatternBuilder builder = BlockPatternBuilder.start();
            String[] aisleStrings = pattern.getPattern().toArray(new String[0]);
            builder.aisle(aisleStrings);
            for (String pat : pattern.getPattern()) {
                for (int i = 0; i < pat.length(); i++) {
                    char ch = pat.charAt(i);
                    if (ch == head.getKey() && !definedChars.contains(ch)) {
                        builder.where(ch, blockInWorld -> {
                            if (!blockInWorld.getState().is(JBPTags.BLACK_LIST)) {
                                if (getHead().getBlock() != null) {
                                    return blockInWorld.getState().is(getHead().getBlock());
                                } else {
                                    return blockInWorld.getState().is(getHead().getTag());
                                }
                            }
                            return false;
                        });
                        definedChars.add(ch);
                    }
                    for (KeyDefinitionCodec baseKeys : base) {
                        if (ch == baseKeys.getKey() && !definedChars.contains(ch)) {
                            if (baseKeys.getBlock() != null || baseKeys.getTag() != null) {
                                builder.where(ch, blockInWorld -> {
                                    if (!blockInWorld.getState().is(JBPTags.BLACK_LIST)) {
                                        if (baseKeys.getBlock() != null) {
                                            return blockInWorld.getState().is(baseKeys.getBlock());
                                        } else {
                                            return blockInWorld.getState().is(baseKeys.getTag());
                                        }
                                    }
                                    return false;
                                });
                            }
                            definedChars.add(ch);
                        }
                    }
                }
            }
            this.blockPattern = builder.build();
        }


        return blockPattern;
    }

    @Override
    public KeyDefinitionCodec getHead() {
        return head;
    }

    @Override
    public List<KeyDefinitionCodec> getBase() {
        return base;
    }

    @Override
    public EntityType<?> getEntity() {
        return entityType;
    }

    @Override
    public BlockPatternBuilderType<? extends BlockPatternBuilderCodec> type() {
        return BlockPatternBuilderRegistry.NORMAL_PATTERN.get();
    }
}
