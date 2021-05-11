package io.github.discstudiosclient.mixin.message;

import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.event.ChatRecievedEvent;
import io.github.discstudiosclient.event.CommandEvent;
import io.github.discstudiosclient.util.ChatType;
import io.github.discstudiosclient.util.ChatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.*;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.Console;

@Mixin(ClientPlayerEntity.class)
public class MessageSenderMixin {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "Lnet/minecraft/client/network/ClientPlayerEntity;sendChatMessage(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    public void onMessage(String string, CallbackInfo ci) {
        String[] args = string.split(" ");
        if (string.startsWith("?")) {
            CommandEvent.onCommand(string, ci, args);
            ci.cancel();
        }

        if (string.startsWith("/discord")) {
            if (Main.MC.getInstance().getCurrentServerEntry().address.contains("mcdiamondfire.com")) {
                ChatUtil.sendMessage("§7Official Discord Links:", ChatType.SUCCESS);
                Text df = new LiteralText("§7DiamondFire: https://discord.gg/pDHBbBD")
                        .styled(s -> s.withHoverEvent(
                                new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("§7Click to open!"))
                        ).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/pDHBbBD")));
                ChatUtil.sendMessage((MutableText) df, ChatType.SUCCESS);

                Text ds = new LiteralText("§7Disc Studios: https://discord.gg/acTHnGE49D")
                        .styled(s -> s.withHoverEvent(
                                new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("§7Click to open!"))
                        ).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/acTHnGE49D")));
                ChatUtil.sendMessage((MutableText) ds, ChatType.SUCCESS);

                ci.cancel();
            }
        }
    }
}