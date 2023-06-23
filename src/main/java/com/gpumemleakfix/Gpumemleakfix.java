package com.gpumemleakfix;

import com.gpumemleakfix.config.Configuration;
import com.gpumemleakfix.event.EventHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static com.gpumemleakfix.Gpumemleakfix.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class Gpumemleakfix
{
    public static final String        MOD_ID = "gpumemleakfix";
    public static final Logger        LOGGER = LogManager.getLogger();
    private static      Configuration config = null;
    public static       Random        rand   = new Random();

    // GL_OUT_OF_MEMORY
    // GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT
    // GL_INVALID_FRAMEBUFFER_OPERATION

    public Gpumemleakfix()
    {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> "", (a, b) -> true));
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(EventHandler.class);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
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

    public static Configuration getConfig()
    {
        if (config == null)
        {
            config = new Configuration();
            config.load();
        }

        return config;
    }
}
