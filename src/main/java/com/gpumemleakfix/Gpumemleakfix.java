package com.gpumemleakfix;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gpumemleakfix.Gpumemleakfix.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class Gpumemleakfix
{
    public static final String MOD_ID = "gpumemleakfix";
    public static final Logger LOGGER = LogManager.getLogger();

    // GL_OUT_OF_MEMORY
    // GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT
    // GL_INVALID_FRAMEBUFFER_OPERATION

    public Gpumemleakfix(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
    }

    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event)
    {
        // Side safe client event handler
        GpumemleakfixClient.onInitializeClient(event);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info(MOD_ID + " mod initialized");
    }
}
