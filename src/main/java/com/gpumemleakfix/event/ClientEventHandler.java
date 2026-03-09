package com.gpumemleakfix.event;

import com.gpumemleakfix.Gpumemleakfix;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientEventHandler
{

    public static ConcurrentLinkedQueue<List<AutoCloseable>> queue = new ConcurrentLinkedQueue<>();

    /**
     * Checks on tick for leaked adresses and cleans them up
     */
    @SubscribeEvent()
    public static void onCLientTick(final ClientTickEvent.Post event)
    {
        int counter = 0;
        while (!queue.isEmpty() && counter++ < 20)
        {
            // destroybuffer from Rendertarget
            final List<AutoCloseable> textures = queue.poll();
            for (final AutoCloseable closeable : textures)
            {
                try
                {
                    closeable.close();
                }
                catch (Exception e)
                {
                    Gpumemleakfix.LOGGER.warn("Failed to close texture: "+closeable, e);
                }
            }
        }
    }
}
