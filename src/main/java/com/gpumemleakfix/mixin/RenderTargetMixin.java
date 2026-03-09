package com.gpumemleakfix.mixin;

import com.gpumemleakfix.Gpumemleakfix;
import com.gpumemleakfix.event.ClientEventHandler;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = RenderTarget.class, remap = false)
public abstract class RenderTargetMixin
{
    @Shadow(remap = true)
    @Nullable
    protected GpuTexture colorTexture;

    @Shadow(remap = true)
    @Nullable
    protected GpuTexture depthTexture;

    @Shadow
    @org.jspecify.annotations.Nullable
    protected GpuTextureView depthTextureView;

    @Shadow
    @org.jspecify.annotations.Nullable
    protected GpuTextureView colorTextureView;

    @Override
    public void finalize() throws Throwable
    {
        try
        {
            if (this.colorTexture != null || this.depthTexture != null || this.depthTextureView != null || this.colorTextureView != null)
            {
                final List<AutoCloseable> toClose = new ArrayList<>();
                if (this.colorTexture != null)
                {
                    toClose.add(this.colorTexture);
                }
                if (this.depthTexture != null)
                {
                    toClose.add(this.depthTexture);
                }
                if (this.depthTextureView != null)
                {
                    toClose.add(this.depthTextureView);
                }
                if (this.colorTextureView != null)
                {
                    toClose.add(this.colorTextureView);
                }

                ClientEventHandler.queue.add(toClose);
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
