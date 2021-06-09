package io.github.discstudiosclient.mixin.message;

import io.github.discstudiosclient.event.ChatRecievedEvent;
import io.github.discstudiosclient.util.MessageGrabber;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MessageListenerMixin {
    private static long lastPatchCheck = 0;
    private static long lastBuildCheck = 0;
    private final MinecraftClient minecraftClient = MinecraftClient.getInstance();
    private boolean motdShown = false;

    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        if (MessageGrabber.isActive()) {
            MessageGrabber.supply(packet.getMessage());

            if (MessageGrabber.isSilent()) {
                ci.cancel();
            }
        }

        ChatRecievedEvent.onMessage(packet.getMessage(), ci);
    }
}