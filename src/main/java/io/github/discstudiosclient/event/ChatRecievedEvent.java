package io.github.discstudiosclient.event;

import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.util.ChatType;
import io.github.discstudiosclient.util.ChatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatRecievedEvent {

    public static int cancelMsgs = 0;
    public static boolean gettingKey = true;
    public static boolean keyCollected = false;
    public static String returnNode = "node5";
    public static String key = "none";
    public static String currentPatch = "none";

    public static boolean locateParser = false;
    public static String locateParser_id = "none";
    public static String locateParser_name = "none";
    public static String locateParser_node = "none";
    public static String locateParser_command = "none";

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

        if (text.startsWith("Current patch: ")) {
            currentPatch = text.replace("Current patch: ", "");
            currentPatch = currentPatch.replace(" See the patch notes with /patch!", "");
        }

        if (gettingKey) {
            cancel = true;
        }

        /*
        if (text.equals("◆ Welcome back to DiamondFire! ◆")) {
            if (!keyCollected) {
                mc.player.sendChatMessage("/server " + returnNode);
                mc.player.sendChatMessage("/join 52174");
                gettingKey = true;
            }
        }

         */

        if (locateParser) {
            if (text.equals("Error: Could not find that player.")) {
                locateParser_name = "Offline";
                locateParser_node = "Offline";
                locateParser_id = "Offline";

                cancel = true;
                locateParser = false;
            }

            String msg = text.replaceAll("§.", "");
            if (msg.startsWith("                                       \n")) {
                if (msg.contains(" is currently at spawn\n")) {
                    locateParser_name = "Spawn";
                    locateParser_id = "none";
                    // NODE
                    Pattern pattern = Pattern.compile("Node ([0-9]|Beta)\n");
                    Matcher matcher = pattern.matcher(text);
                    String node = "";
                    while (matcher.find()) {
                        locateParser_node = matcher.group();
                    }

                    if (locateParser_node.replace("Node ", "").equals("Beta")) {
                        locateParser_command = "/server beta";
                    } else {
                        locateParser_command = "/server " + locateParser_node.replace("Node ", "");
                    }

                } else if (msg.contains(" are currently at spawn\n")) {
                    locateParser_name = "Spawn";
                    locateParser_id = "none";
                    // NODE
                    Pattern pattern = Pattern.compile("Node ([0-9]|Beta)\n");
                    Matcher matcher = pattern.matcher(text);
                    String node = "";
                    while (matcher.find()) {
                        locateParser_node = matcher.group();
                    }

                    if (locateParser_node.contains("Beta")) {
                        locateParser_command = "/server beta";

                    } else {
                        locateParser_command = "/server node" + locateParser_node.replace("Node ", "").replace("\n", "");
                    }

                } else {
                    // PLOT ID
                    Pattern pattern = Pattern.compile("\\[[0-9]+]\n");
                    Matcher matcher = pattern.matcher(text);
                    String id = "";
                    while (matcher.find()) {
                        locateParser_id = matcher.group();
                    }
                    locateParser_id = locateParser_id.replaceAll("[\\[\\]\n]", "");
                    locateParser_command = "/join " + locateParser_id;


                    // PLOT NAME
                    pattern = Pattern.compile("\n\n→ .+ \\[[0-9]+]\n");
                    matcher = pattern.matcher(text);
                    String name = "";
                    while (matcher.find()) {
                        locateParser_name = matcher.group();
                    }
                    locateParser_name = locateParser_name.replaceAll("(^\n\n→ )|( \\[[0-9]+]\n$)", "");
                }

                cancel = true;
                locateParser = false;

            }
        }

        if (text.startsWith("Error: You are not whitelisted on this plot!")) {
            if (gettingKey) {
                gettingKey = false;
                keyCollected = true;
                key = "None";

                ChatUtil.sendMessage("§4◆ §bWelcome back to DiamondFire! §4◆");
                ChatUtil.sendMessage("§eCurrent patch: §a" + currentPatch + "§e See the patch notes with /patch!");

                ChatUtil.sendMessage(" ");
                ChatUtil.sendMessage("Thanks for using Disc Studios Client!", ChatType.SUCCESS);
                ChatUtil.sendMessage("Use ?help for a list of commands!", ChatType.SUCCESS);
            }
        }

        if (text.startsWith("DS_Client_Key: ")) {
            key = text.replace("DS_Client_Key: ", "");
            gettingKey = false;

            if (!keyCollected) {
                keyCollected = true;
                Main.log(Level.INFO, "Chat key has been sucsessfully set to: " + key);
                mc.player.sendChatMessage("/spawn");
                ChatUtil.sendMessage("§4◆ §bWelcome back to DiamondFire! §4◆");
                ChatUtil.sendMessage("§eCurrent patch: §a" + currentPatch + "§e See the patch notes with /patch!");

                ChatUtil.sendMessage(" ");
                ChatUtil.sendMessage("Thanks for using Disc Studios Client!", ChatType.SUCCESS);
                ChatUtil.sendMessage("Use ?help for a list of commands!", ChatType.SUCCESS);

                Main.MC.player.playSound(SoundEvents.UI_LOOM_TAKE_RESULT, SoundCategory.MASTER, 50, 2);

            } else {
                Main.log(Level.INFO, "Chat key has been sucsessfully updated to: " + key);

            }

            cancel = true;
        }

        if (cancel) {
            ci.cancel();
        }
    }
}