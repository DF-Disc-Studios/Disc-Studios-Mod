package io.github.discstudiosclient.mixin.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.util.BlendableTexturedButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow
    private String splashText;
    @Shadow
    private int copyrightTextX;
    @Shadow
    private static Identifier EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");

    @Shadow
    private static Identifier MINECRAFT_TITLE_TEXTURE = new Identifier("textures/gui/title/minecraft.png");


    private final Identifier accessability_button = new Identifier("textures/gui/accessibility.png");
    //private final Identifier df_button = new Identifier(Main.MOD_ID, "textures/assets/discstudiosclient/df.png");

    protected TitleScreenMixin(Text title) {
        super(title);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/RotatingCubeMapRenderer;render(FF)V"))
    private void render(RotatingCubeMapRenderer rotatingCubeMapRenderer, float delta, float alpha, MatrixStack matrices, int mouseX, int mouseY, float d) {
        rotatingCubeMapRenderer.render(delta, alpha);
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    public void init(CallbackInfo callbackInfo) {
        assert this.client != null;
        this.client.options.realmsNotifications = false;
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    protected void initButtons(CallbackInfo info) {

        //MINECRAFT_TITLE_TEXTURE = new Identifier(Main.MOD_ID, "textures/assets/discstudiosclient/empty.png");
        //EDITION_TITLE_TEXTURE = new Identifier(Main.MOD_ID, "textures/assets/discstudiosclient/empty.png");


        splashText = null;

        copyrightTextX = 1000000000;

        for (AbstractButtonWidget button : this.buttons) {
            button.visible = false;
        }


        this.addButton(new ButtonWidget(this.width - (this.width - 10), this.height - (this.height - 110), 150, 20, new LiteralText("Play"), (buttonWidget) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.openScreen(new MultiplayerScreen(mc.currentScreen));
        }));


        this.addButton(new ButtonWidget(this.width - (this.width - 10), this.height - (this.height - 140), 120, 20, new LiteralText("DiamondFire"), (buttonWidget) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            ServerInfo serverInfo = new ServerInfo("DF", "node5.mcdiamondfire.com:25565", false);
            mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverInfo));
        }));

        this.addButton(new ButtonWidget(this.width - (this.width - 10), this.height - (this.height - 170), 120, 20, new LiteralText("Quit Game"), (buttonWidget) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            System.exit(1);
        }));

        /*
        this.addButton(new BlendableTexturedButtonWidget(this.width - 30, this.height - 30, 20, 20, 0, 0, 20, df_button, 20, 40,
                (button) -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    ServerInfo serverInfo = new ServerInfo("DF", "mcdiamondfire.com:25565", false);
                    mc.openScreen(new ConnectScreen(mc.currentScreen, mc, serverInfo));
                }));

        this.addButton(new BlendableTexturedButtonWidget(this.width - 60, this.height - 30, 20, 20, 0, 0, 20, accessability_button, 20, 40,
                (button) -> {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    mc.openScreen(new AccessibilityOptionsScreen(mc.currentScreen, mc.options));
                }));


         */
    }

}

