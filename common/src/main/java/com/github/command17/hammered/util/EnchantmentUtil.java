package com.github.command17.hammered.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class EnchantmentUtil {
    public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment) {
        return EnchantmentHelper.get(stack).containsKey(enchantment);
    }

    public static int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        if (hasEnchantment(stack, enchantment)) return EnchantmentHelper.get(stack).get(enchantment);

        return 0;
    }
}
