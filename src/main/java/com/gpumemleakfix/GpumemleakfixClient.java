package com.gpumemleakfix;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class GpumemleakfixClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
    }
}
