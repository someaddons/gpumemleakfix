package com.gpumemleakfix.event;

import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.util.Tuple;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientEventHandler {

    public static ConcurrentLinkedQueue<Tuple<GpuTexture, GpuTexture>> queue = new ConcurrentLinkedQueue<>();

    /**
     * Checks on tick for leaked adresses and cleans them up
     */
    public static void onCLientTick() {
        int counter = 0;
        while (!queue.isEmpty() && counter++ < 20) {
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
