package com.scouter.jsonblockpatterns.datagen;

import com.scouter.jsonblockpatterns.JsonBlockPatterns;
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
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class WikiPageGenerator extends WikiPageBuilderProvider {
    public WikiPageGenerator(PackOutput output) {
        super(output, "wiki", JsonBlockPatterns.MODID);
    }

    @Override
    protected void generateWikiPages(BiConsumer<String, Supplier<String>> consumer) {
        createWikiPage("Home", homePage(), consumer);
    }


    private WikiPageBuilder homePage() {
        WikiPageBuilder builder = new WikiPageBuilder();

        builder.addHeading("Json Entity Block Patterns", 3);

        List<String> definitionsList2 = new ArrayList<>();
        definitionsList2.add("i  ");
        definitionsList2.add("www");

        BlockPatternBuilderCodec patternBuilderCodec2 = new NormalBlockPatternBuilder(
                new BlockPatternDefinition(definitionsList2),
                KeyDefinition.ofBlock("i", Blocks.CARVED_PUMPKIN),
                List.of(
                        KeyDefinition.ofTag("w", BlockTags.WOOL),
                        KeyDefinition.ofTag(" ", JBPTags.ALL)
                ),
                EntityType.SHEEP
        );

        builder.addParagraph("This mod allows you to define block patterns to spawn entities, similar to Iron Golems, Snow Golems, or Wither Bosses.");
        builder.addParagraph("Below is an example of how the JSON file should look:");
        builder.addCodeBlock(encodeDataToJsonString(BlockPatternBuilderCodec.DIRECT_CODEC, patternBuilderCodec2));
        builder.addParagraph("This JSON defines a block pattern that spawns a sheep when built.");
        builder.addParagraph("The pattern type should be `jsonblockpatterns:normal`, and the keys defined for the base and head should be `jsonblockpatterns:key`.");
        builder.addParagraph("In `jsonblockpatterns:key`, there are three possible definitions: `char`, `tag`, or `block`. The character should match the one defined in the pattern with type `jsonblockpatterns:pattern`.");
        builder.addParagraph("The `jsonblockpatterns:pattern` will have `keys` defined as a list of strings where you can specify how the entity should be built.");
        builder.addParagraph("Only one `head` key can be defined, but multiple `base` block keys can be defined.");
        builder.addParagraph("Under `entity` the entity to spawn should be defined, which can be from vanilla or from mods.");

        builder.addParagraph("The mod adds two tags: `jsonblockpatterns:all`, which can be used for definitions where all blocks are applicable, and `jsonblockpatterns:black_list`, which comprises a blacklist of blocks that cannot be used.");


        builder.addParagraph("Patterns should be defined under `data/<id>/pattern_definitions/pattern`.");

        return builder;
    }


    private void createWikiPage(String filename, WikiPageBuilder builder, BiConsumer<String, Supplier<String>> consumer) {
        consumer.accept(filename, builder::getContent);
    }
}
