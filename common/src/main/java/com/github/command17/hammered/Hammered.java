package com.github.command17.hammered;

import com.github.command17.hammered.enchantment.ModEnchantments;
import com.github.command17.hammered.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hammered {
	public static final String MOD_ID = "hammered";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Initializing...");

		ModItems.register();
		ModEnchantments.register();

		registerVillagerTrades();

		LOGGER.info("Initialized");
	}

	private static void registerVillagerTrades() {
		/*
		TradeRegistry.registerVillagerTrade(VillagerProfession.LIBRARIAN, 2, (entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, random.nextBetween(12, 43)),
				new ItemStack(Items.BOOK),
                EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.HAMMERED.get(), random.nextBoolean() ? 1 : random.nextBetween(2, 3))),
                3,
                5,
                0.02f
        ));

		TradeRegistry.registerVillagerTrade(VillagerProfession.LIBRARIAN, 2, (entity, random) -> new TradeOffer(
				new ItemStack(Items.EMERALD, random.nextBetween(6, 30)),
				new ItemStack(Items.BOOK),
				EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.IMPACT.get(), random.nextBetween(1, 2))),
				3,
				5,
				0.02f
		));
		 */

		LOGGER.info("Registered Villager Trades.");
	}
}
