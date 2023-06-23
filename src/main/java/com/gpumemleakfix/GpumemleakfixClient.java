package com.gpumemleakfix;

import com.gpumemleakfix.event.ClientEventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class GpumemleakfixClient
{
    public static void onInitializeClient(final FMLClientSetupEvent event)
    {
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(ClientEventHandler.class);
    }
}
