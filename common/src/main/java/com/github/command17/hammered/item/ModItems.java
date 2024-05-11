package com.github.command17.hammered.item;

import com.github.command17.hammered.Hammered;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.RegistryKeys;

public class ModItems {
    private static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(Hammered.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> IRON_HAMMER = ITEM_REGISTRY.register("iron_hammer",
            () -> new HammerItem(ToolMaterials.IRON, 5, -2.8f, 1.3f, new Item.Settings().arch$tab(ItemGroups.TOOLS)));

    public static final RegistrySupplier<Item> GOLD_HAMMER = ITEM_REGISTRY.register("gold_hammer",
            () -> new HammerItem(ToolMaterials.GOLD, 5, -2.8f, 1.3f, new Item.Settings().arch$tab(ItemGroups.TOOLS)));

    public static final RegistrySupplier<Item> DIAMOND_HAMMER = ITEM_REGISTRY.register("diamond_hammer",
            () -> new HammerItem(ToolMaterials.DIAMOND, 4, -2.8f, 1.3f, new Item.Settings().arch$tab(ItemGroups.TOOLS)));

    public static final RegistrySupplier<Item> NETHERITE_HAMMER = ITEM_REGISTRY.register("netherite_hammer",
            () -> new HammerItem(ToolMaterials.NETHERITE, 4, -2.8f, 1.3f, new Item.Settings().fireproof().arch$tab(ItemGroups.TOOLS)));

    public static void register() {
        ITEM_REGISTRY.register();

        Hammered.LOGGER.info("Registered Items.");
    }
}
