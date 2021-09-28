package net.aquarius.client.mixin.mixins;

import net.aquarius.client.concurrent.MainThreadExecutor;
import net.aquarius.client.config.ConfigManager;
import net.aquarius.client.event.EventBus;
import net.aquarius.client.event.events.client.ClientTickEvent;
import net.aquarius.client.event.events.client.InputUpdateEvent;
import net.aquarius.client.event.events.client.KeyEvent;
import net.aquarius.client.event.events.client.SafeClientEvent;
import net.aquarius.client.launch.InitManager;
import net.aquarius.client.management.InputManager;
import net.aquarius.client.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Util;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mixin(value = Minecraft.class, priority = Integer.MAX_VALUE - 312)
public class MixinMinecraft {

    @Shadow
    public GuiScreen currentScreen;

    @Inject(method = "init", at = @At("HEAD"))
    private void onMinecraftInitHead(CallbackInfo ci) {
        Logger.info("Registering Spartan Mods");
        InitManager.INSTANCE.onMinecraftInit();
    }

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    private void onRunningGameLoopHead(CallbackInfo ci) {
        SafeClientEvent.Companion.update();
        MainThreadExecutor.INSTANCE.runJobs();
    }

    @Inject(method = "runGameLoop", at = @At("RETURN"))
    private void onRunningGameLoopReturn(CallbackInfo ci) {
        MainThreadExecutor.INSTANCE.runJobs();
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    private void onRunTick(CallbackInfo ci) {
        ClientTickEvent.INSTANCE.post();
        MainThreadExecutor.INSTANCE.runJobs();
    }

    @Inject(method = "init",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    private void onPreInit(CallbackInfo ci) {
        InitManager.preInitHook();
        setIcon();
    }

    private void setIcon() {
        Util.EnumOS os = Util.getOSType();
        if (os != Util.EnumOS.OSX) {
            try {
                InputStream inputStream = getResourceStream("/assets/aquarius/logo32.png");
                InputStream inputStream1 = getResourceStream("/assets/aquarius/logo16.png");
                if (inputStream != null && inputStream1 != null) {
                    Display.setIcon(new ByteBuffer[]{readImageToBuffer(inputStream), readImageToBuffer(inputStream1)});
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(imageStream);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

        for (int i : aint) {
            bytebuffer.putInt(i << 8 | i >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }

    public InputStream getResourceStream(String path) {
        return MixinMinecraft.class.getResourceAsStream(path);
    }

    @Inject(method = "init",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V",
                    ordinal = 2,
                    shift = At.Shift.AFTER
            )
    )
    private void onPostInit(CallbackInfo ci) {
        InitManager.postInitHook();
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo info) {
        ConfigManager.INSTANCE.saveAll(true);
    }

    @Inject(method = "runTickKeyboard", at = @At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Keyboard.getEventKeyState()Z", remap = false))
    private void onKeyEvent(CallbackInfo ci) {
        if (currentScreen != null)
            return;

        boolean down = Keyboard.getEventKeyState();
        int key = Keyboard.getEventKey();
        char ch = Keyboard.getEventCharacter();

        //Prevent from toggling all keys,when switching languages.
        if (key != Keyboard.KEY_NONE) {
            EventBus.post(down ? new KeyEvent(key, ch) : new InputUpdateEvent(key, ch));
            if (down) InputManager.onKey(key);
        }

    }

}