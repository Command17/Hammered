package com.github.command17.hammered.enchantment;

import com.github.command17.hammered.Hammered;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.RegistryKeys;

public class ModEnchantments {
    private static final DeferredRegister<Enchantment> ENCHANTMENT_REGISTRY = DeferredRegister.create(Hammered.MOD_ID, RegistryKeys.ENCHANTMENT);

    public static final EquipmentSlot[] MAIN_HAND = new EquipmentSlot[] {EquipmentSlot.MAINHAND};

    public static final RegistrySupplier<Enchantment> HAMMERED = ENCHANTMENT_REGISTRY.register("hammered",
            () -> new HammeredEnchantment(Enchantment.Rarity.RARE, MAIN_HAND));

    public static final RegistrySupplier<Enchantment> IMPACT = ENCHANTMENT_REGISTRY.register("impact",
            () -> new ImpactEnchantment(Enchantment.Rarity.UNCOMMON, MAIN_HAND));

    public static void register() {
        ENCHANTMENT_REGISTRY.register();

        Hammered.LOGGER.info("Registered Enchantments.");
    }
}
