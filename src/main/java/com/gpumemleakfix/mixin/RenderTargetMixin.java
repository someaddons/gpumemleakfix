package com.gpumemleakfix.mixin;

import com.gpumemleakfix.Gpumemleakfix;
import com.gpumemleakfix.event.ClientEventHandler;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.util.Tuple;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = RenderTarget.class, remap = false)
public abstract class RenderTargetMixin
{
    @Shadow(remap = true)
    @Nullable
    protected GpuTexture colorTexture;

    @Shadow(remap = true)
    @Nullable
    protected GpuTexture depthTexture;

    @Override
    public void finalize() throws Throwable
    {
        try
        {
            if (this.colorTexture != null || this.depthTexture != null)
            {
                ClientEventHandler.queue.add(new Tuple<>(colorTexture, depthTexture));
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
