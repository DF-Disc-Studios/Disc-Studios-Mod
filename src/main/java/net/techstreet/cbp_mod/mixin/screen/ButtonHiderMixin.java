package net.techstreet.cbp_mod.mixin.screen;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.techstreet.cbp_mod.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TitleScreen.class)
public class ButtonHiderMixin extends Screen {
    @Shadow
    public static final CubeMapRenderer PANORAMA_CUBE_MAP = new CubeMapRenderer(new Identifier("textures/gui/title/background/panorama"));
    @Shadow
    private String splashText;
    @Shadow
    private int copyrightTextX;
    @Shadow
    private static Identifier EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");
    @Shadow
    private static Identifier MINECRAFT_TITLE_TEXTURE = new Identifier("textures/gui/title/minecraft.png");

    protected ButtonHiderMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    protected void init(CallbackInfo info) {
        
        MINECRAFT_TITLE_TEXTURE = new Identifier("Minecraft", Main.MOD_ID + ":empty.png");
        EDITION_TITLE_TEXTURE = new Identifier("JavaEdition", Main.MOD_ID + ":empty.png");

        splashText = null;

        copyrightTextX = 1000000000;

        for (AbstractButtonWidget button : this.buttons) {
            button.visible = false;
        }
    }

}