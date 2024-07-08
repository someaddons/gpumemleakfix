package com.gpumemleakfix;

import com.gpumemleakfix.event.ClientEventHandler;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

public class GpumemleakfixClient
{
    public static void onInitializeClient(final FMLClientSetupEvent event)
    {
        NeoForge.EVENT_BUS.register(ClientEventHandler.class);
    }
}
