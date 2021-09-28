package net.aquarius.client.mixin.mixins.render;

import net.aquarius.client.event.events.client.Render3DEvent;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class, priority = Integer.MAX_VALUE)
public class MixinEntityRenderer {

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;clear(I)V", ordinal = 1, shift = At.Shift.AFTER))
    private void onRenderWorldPassClear(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        Render3DEvent.INSTANCE.post();
    }
}
