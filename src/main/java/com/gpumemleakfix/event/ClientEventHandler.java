package com.gpumemleakfix.event;

import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.util.Tuple;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientEventHandler {

    public static ConcurrentLinkedQueue<Tuple<GpuTexture, GpuTexture>> queue = new ConcurrentLinkedQueue<>();

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
            final Tuple<GpuTexture, GpuTexture> ids = queue.poll();
            if (ids != null) {
                if (ids.getA() != null) {
                    ids.getA().close();
                }

                if (ids.getB() != null) {
                    ids.getB().close();
                }
            }
        }
    }
}
