package io.github.discstudiosclient.event;

import io.github.discstudiosclient.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ChatRecievedEvent {

    public static int cancelMsgs = 0;
    public static boolean gettingKey = true;
    public static boolean keyCollected = false;
    public static String returnNode = "node5";
    public static String key = "none";

    public static void onMessage(Text message, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        String text = message.getString();

        boolean cancel = false;

        if (mc.player == null) {
            return;
        }

        if (cancelMsgs > 0) {
            cancelMsgs--;
            cancel = true;
        }

        if (gettingKey) {
            cancel = true;
        }

        if (text.equals("◆ Welcome back to DiamondFire! ◆")) {
            if (!keyCollected) {
                mc.player.sendChatMessage("/server " + returnNode);
                mc.player.sendChatMessage("/join 52174");
                gettingKey = true;
            }
        }

        if (text.startsWith("DS_Client_Key: ")) {
            if (gettingKey) {
                key = text;
                gettingKey = false;
                keyCollected = true;
                text = text.replace("DS_Client_Key: ", "");
                Main.log(Level.INFO, "Chat key has been sucsessfully set to: " + text);
                mc.player.sendChatMessage("/spawn");
            }

            cancel = true;
        }

        if (cancel) {
            ci.cancel();
        }
    }
}