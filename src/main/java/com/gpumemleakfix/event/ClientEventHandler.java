package com.gpumemleakfix.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.core.Vec3i;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientEventHandler
{
    public static ConcurrentLinkedQueue<Vec3i> queue = new ConcurrentLinkedQueue<>();

    @SubscribeEvent()
    public static void onCLientTick(final TickEvent.ClientTickEvent event)
    {
        boolean done = false;

        int counter = 0;
        while (!queue.isEmpty() && counter++ < 20)
        {
            if (!done)
            {
                // Unbindread Unbindwrite as RenderTarget
                RenderSystem.assertOnRenderThreadOrInit();
                GlStateManager._bindTexture(0);

                if (!RenderSystem.isOnRenderThread())
                {
                    RenderSystem.recordRenderCall(() -> {
                        GlStateManager._glBindFramebuffer(36160, 0);
                    });
                }
                else
                {
                    GlStateManager._glBindFramebuffer(36160, 0);
                }

                done = true;
            }

            // destroybuffer from Rendertarget
            final Vec3i ids = queue.poll();
            if (ids != null)
            {
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
