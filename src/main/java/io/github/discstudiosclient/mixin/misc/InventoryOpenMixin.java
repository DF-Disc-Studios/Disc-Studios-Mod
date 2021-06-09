package io.github.discstudiosclient.mixin.misc;

import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.event.InventoryOpenEvent;
import io.github.discstudiosclient.util.ChatUtil;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class InventoryOpenMixin {

    @Inject(method = "onInventory", at = @At("RETURN"))
    private void onInventory(InventoryS2CPacket packet, CallbackInfo ci) {
        InventoryOpenEvent.onOpen(packet, ci);
    }
}
