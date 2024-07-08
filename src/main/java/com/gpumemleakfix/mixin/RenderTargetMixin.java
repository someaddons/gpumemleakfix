package com.gpumemleakfix.mixin;

import com.gpumemleakfix.Gpumemleakfix;
import com.gpumemleakfix.event.ClientEventHandler;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.core.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = RenderTarget.class, remap = false)
public abstract class RenderTargetMixin
{
    @Shadow(remap = true)
    protected int depthBufferId;

    @Shadow(remap = true)
    protected int colorTextureId;

    @Shadow(remap = true)
    public int frameBufferId;

    @Override
    public void finalize() throws Throwable
    {
        try
        {
            if (this.depthBufferId > -1 || this.colorTextureId > -1 || this.frameBufferId > -1)
            {
                ClientEventHandler.queue.add(new Vec3i(depthBufferId, colorTextureId, frameBufferId));
            }
        }
        catch (Throwable t)
        {
            Gpumemleakfix.LOGGER.error("Error during render target finalize:", t);
        }
        finally
        {
            super.finalize();
        }
    }
}
