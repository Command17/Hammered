package com.github.command17.hammered.fabric;

import com.github.command17.hammered.Hammered;
import net.fabricmc.api.ModInitializer;

public class HammeredFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Hammered.init();
    }
}