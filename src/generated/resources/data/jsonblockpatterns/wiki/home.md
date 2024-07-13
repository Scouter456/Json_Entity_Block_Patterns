# 

### Json Entity Block Patterns

This mod allows you to define block patterns to spawn entities, similar to Iron Golems, Snow Golems, or Wither Bosses.

Below is an example of how the JSON file should look:

```
{
  "value": {
    "pattern": {
      "keys": [
        "i  ",
        "www"
      ],
      "type": "jsonblockpatterns:pattern"
    },
    "head": {
      "char": "i",
      "block": "minecraft:carved_pumpkin",
      "type": "jsonblockpatterns:key"
    },
    "base": [
      {
        "char": "w",
        "tag": "minecraft:wool",
        "type": "jsonblockpatterns:key"
      },
      {
        "char": " ",
        "tag": "jsonblockpatterns:all",
        "type": "jsonblockpatterns:key"
      }
    ],
    "entity": "minecraft:sheep",
    "type": "jsonblockpatterns:normal"
  }
}
```

This JSON defines a block pattern that spawns a sheep when built.

The pattern type should be `jsonblockpatterns:normal`, and the keys defined for the base and head should be `jsonblockpatterns:key`.

In `jsonblockpatterns:key`, there are three possible definitions: `char`, `tag`, or `block`. The character should match the one defined in the pattern with type `jsonblockpatterns:pattern`.

The `jsonblockpatterns:pattern` will have `keys` defined as a list of strings where you can specify how the entity should be built.

Only one `head` key can be defined, but multiple `base` block keys can be defined.

Under `entity` the entity to spawn should be defined, which can be from vanilla or from mods.

The mod adds two tags: `jsonblockpatterns:all`, which can be used for definitions where all blocks are applicable, and `jsonblockpatterns:black_list`, which comprises a blacklist of blocks that cannot be used.

Patterns should be defined under `data/<id>/pattern_definitions/pattern`.

