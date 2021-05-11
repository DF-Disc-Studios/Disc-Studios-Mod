package io.github.discstudiosclient.mixin.render;

import io.github.discstudiosclient.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "renderStatusEffectOverlay", at = @At("RETURN"))
    private void init(MatrixStack stack, CallbackInfo ci) {
        if (Main.DISPLAY_TEXT) {
            try {
                String[] text = Main.TEXT.split("\n");
                Integer y = 5;
                for (String t : text) {
                    Main.MC.textRenderer.drawWithShadow(stack, new LiteralText(t), 5, y, 0xffffff);
                    y += 10;
                }

            } catch (Exception e) {
                Main.log(Level.ERROR, e.toString());
            }
        }
    }
}
