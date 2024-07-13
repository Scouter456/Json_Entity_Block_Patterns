package com.scouter.jsonblockpatterns.datagen;

import com.scouter.jsonblockpatterns.data.BlockPatternBuilderCodec;
import com.scouter.jsonblockpatterns.data.BlockPatternDefinition;
import com.scouter.jsonblockpatterns.data.KeyDefinition;
import com.scouter.jsonblockpatterns.data.NormalBlockPatternBuilder;
import com.scouter.jsonblockpatterns.util.JBPTags;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.scouter.jsonblockpatterns.JsonBlockPatterns.prefix;

public class PatternGenerator extends PatternProvider{
    public PatternGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildPatterns(Consumer<PatternConsumer> pWriter) {

        List<String> definitionsList = new ArrayList<>();
        definitionsList.add("i i");
        definitionsList.add("bbb");

        BlockPatternBuilderCodec patternBuilderCodec = new NormalBlockPatternBuilder(
                new BlockPatternDefinition(definitionsList),
                KeyDefinition.ofBlock("i", Blocks.BAMBOO_BLOCK),
                List.of(KeyDefinition.ofBlock("b", Blocks.AMETHYST_BLOCK),
                        KeyDefinition.ofTag(" ", BlockTags.AIR)),
                EntityType.ALLAY
        );

        List<String> definitionsList2 = new ArrayList<>();
        definitionsList2.add("i  ");
        definitionsList2.add("www");

        BlockPatternBuilderCodec patternBuilderCodec2 = new NormalBlockPatternBuilder(
                new BlockPatternDefinition(definitionsList2),
                KeyDefinition.ofBlock("i", Blocks.CARVED_PUMPKIN),
                List.of(KeyDefinition.ofTag("w", BlockTags.WOOL),
                         KeyDefinition.ofTag(" ", JBPTags.ALL)),
                        EntityType.SHEEP
        );

        pWriter.accept(new PatternConsumer(prefix("allay_pattern"), patternBuilderCodec));
        pWriter.accept(new PatternConsumer(prefix("sheep_pattern"), patternBuilderCodec2));
    }
}
