package com.github.command17.hammered.forge;

import dev.architectury.platform.forge.EventBuses;
import com.github.command17.hammered.Hammered;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Hammered.MOD_ID)
public class HammeredForge {
    public HammeredForge() {
        EventBuses.registerModEventBus(Hammered.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        Hammered.init();
    }
}