package com.gpumemleakfix.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.core.Vec3i;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientEventHandler
{
    public static ConcurrentLinkedQueue<Vec3i> queue = new ConcurrentLinkedQueue<>();

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
            final Vec3i ids = queue.poll();
            if (ids != null)
            {
                // Unbindread Unbindwrite as RenderTarget
                GlStateManager._bindTexture(0);
                GlStateManager._glBindFramebuffer(36160, 0);

                if (ids.getX() > -1)
                {
                    TextureUtil.releaseTextureId(ids.getX());
                }

                if (ids.getY() > -1)
                {
                    TextureUtil.releaseTextureId(ids.getY());
                }

                if (ids.getZ() > -1)
                {
                    GlStateManager._glBindFramebuffer(36160, 0);
                    GlStateManager._glDeleteFramebuffers(ids.getZ());
                }
            }
        }
    }
}
