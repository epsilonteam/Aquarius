package net.spartanb312.aquarius.mixin.mixins.render;

import net.spartanb312.aquarius.event.events.client.Render2DEvent;
import net.spartanb312.aquarius.util.graphics.ResolutionHelper;
import net.minecraft.client.gui.GuiSubtitleOverlay;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSubtitleOverlay.class)
public class MixinGuiSubtitleOverlay {

    @Inject(method = "renderSubtitles", at = @At("HEAD"))
    public void onRender2D(ScaledResolution resolution, CallbackInfo ci) {
        ResolutionHelper.reset();
        Render2DEvent.INSTANCE.post();
    }

}
