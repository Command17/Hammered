package com.github.command17.hammered.enchantment;

import com.github.command17.hammered.item.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class HammeredEnchantment extends Enchantment {
    public HammeredEnchantment(Rarity rarity, EquipmentSlot[] slotTypes) {
        super(rarity, EnchantmentTarget.DIGGER, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof HammerItem;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
