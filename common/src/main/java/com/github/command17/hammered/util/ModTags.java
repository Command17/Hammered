package com.github.command17.hammered.util;

import com.github.command17.hammered.Hammered;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class ItemTag {
        public static final TagKey<Item> HAMMERS = tag("hammers");

        private static TagKey<Item> tag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(Hammered.MOD_ID, name));
        }
    }

    public static class BlockTag {
        private static TagKey<Block> tag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(Hammered.MOD_ID, name));
        }
    }
}
