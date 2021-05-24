package io.github.discstudiosclient.event;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.util.ChatType;
import io.github.discstudiosclient.util.ChatUtil;
import io.github.discstudiosclient.util.TextUtil;
import net.minecraft.text.*;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CommandEvent {

    public static void onCommand(String string, CallbackInfo ci, String[] args) {
        new Thread(() -> {
            if (string.startsWith("?help")) {
                ChatUtil.sendMessage("§7§m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m ", ChatType.SUCCESS);
                ChatUtil.sendMessage("§b→ §6?chat <message> §8- §fView the profile of a user.", ChatType.SUCCESS);
                ChatUtil.sendMessage("§7§m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m §m ", ChatType.SUCCESS);

            } else if (string.startsWith("?profile")) {
                if (args.length >= 2) {
                    try {
                        String sURL = "https://api.mojang.com/users/profiles/minecraft/" + args[1];
                        URL url = new URL(sURL);
                        URLConnection request = url.openConnection();
                        request.connect();

                        JsonParser jp = new JsonParser();
                        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                        JsonObject rootobj = root.getAsJsonObject();

                        String uuid = rootobj.get("id").getAsString();

                        sURL = "https://raw.githubusercontent.com/DF-Disc-Studios/Data/main/badges.json";
                        url = new URL(sURL);
                        request = url.openConnection();
                        request.connect();

                        jp = new JsonParser();
                        root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                        JsonObject badges = root.getAsJsonObject();

                        try {
                            sURL = "https://raw.githubusercontent.com/DF-Disc-Studios/Data/main/users/" + TextUtil.formUUID(uuid) + ".json";
                            url = new URL(sURL);
                            request = url.openConnection();
                            request.connect();

                            jp = new JsonParser();
                            root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                            rootobj = root.getAsJsonObject();

                        } catch (Exception e) {
                            sURL = "https://raw.githubusercontent.com/DF-Disc-Studios/Data/main/users/default.json";
                            url = new URL(sURL);
                            request = url.openConnection();
                            request.connect();

                            jp = new JsonParser();
                            root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                            rootobj = root.getAsJsonObject();

                        }

                        Main.MC.player.sendChatMessage("/locate " + args[1]);
                        ChatRecievedEvent.locateParser = true;

                        Integer index = 0;
                        while (index != 5000) {
                            index += 1;
                            if (index == 2000) {
                                ChatUtil.sendMessage("Timeout error while trying to find that player.", ChatType.FAIL);
                                ChatRecievedEvent.locateParser = false;
                                return;
                            }

                            if (ChatRecievedEvent.locateParser == false) {
                                break;
                            }
                            Thread.sleep(1);
                        }

                        // Header
                        ChatUtil.sendMessage("&7 &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m".replace("&", "§"), ChatType.SUCCESS);
                        ChatUtil.sendMessage("  §7Disc Studios Profile of §f" + args[1] +":", ChatType.SUCCESS);
                        ChatUtil.sendMessage("  §7", ChatType.SUCCESS);

                        // Ranks
                        Text ranks = new LiteralText("  §b→ §7Ranks: ").append(TextUtil.colorCodesToTextComponent(rootobj.get("ranks").getAsString().replace("&", "§")));
                        ChatUtil.sendMessage((MutableText) ranks, ChatType.SUCCESS);

                        // UUID
                        Text text = new LiteralText(TextUtil.formUUID(uuid))
                                .styled(s -> s.withHoverEvent(
                                        new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("§7Click to copy!"))
                                ).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "?copytxt " + TextUtil.formUUID(uuid))));
                        text = new LiteralText("  §7§b→ §7UUID: ").append(text);
                        ChatUtil.sendMessage((MutableText) text, ChatType.SUCCESS);

                        // Location
                        if (ChatRecievedEvent.locateParser_name.equals("Offline")) {
                            Text location = new LiteralText("§6" + ChatRecievedEvent.locateParser_name);

                            location = new LiteralText("  §b→ §7Location: ").append(location);
                            ChatUtil.sendMessage((MutableText) location, ChatType.SUCCESS);

                        } else if (ChatRecievedEvent.locateParser_name.equals("Spawn")) {
                            Text location = new LiteralText("§6" + ChatRecievedEvent.locateParser_name + " §8[§f" + ChatRecievedEvent.locateParser_node.replace("\n", "") + "§8]")
                                    .styled(s -> s.withHoverEvent(
                                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("§7Click to join!"))
                                    ).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ChatRecievedEvent.locateParser_command)));
                            Main.log(Level.INFO, ChatRecievedEvent.locateParser_command);

                            location = new LiteralText("  §b→ §7Location: ").append(location);
                            ChatUtil.sendMessage((MutableText) location, ChatType.SUCCESS);

                        } else {
                            Text location = new LiteralText("§6" + ChatRecievedEvent.locateParser_name + " §8[§f" + ChatRecievedEvent.locateParser_id + "§8]")
                                    .styled(s -> s.withHoverEvent(
                                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("§7Click to join!"))
                                    ).withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ChatRecievedEvent.locateParser_command)));

                            location = new LiteralText("  §b→ §7Location: ").append(location);
                            ChatUtil.sendMessage((MutableText) location, ChatType.SUCCESS);

                        }

                        // Badges
                        String image;
                        String tooltip;
                        Text finalBadges = new LiteralText("  §b→ §7Badges: ");

                        String[] userBadges = rootobj.get("badges").getAsString().split(" ");

                        for (String badge : userBadges) {
                            image = badges.get(badge + ".image").getAsString();
                            tooltip = badges.get(badge + ".tooltip").getAsString();

                            String finalTooltip = tooltip;

                            Text badgeFinal = new LiteralText(image + " ")
                                    .styled(s -> s.withHoverEvent(
                                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText(finalTooltip))
                                    ));

                            finalBadges = finalBadges.shallowCopy().append(badgeFinal);
                        }


                        ChatUtil.sendMessage((MutableText) finalBadges, ChatType.SUCCESS);

                        // Footer
                        ChatUtil.sendMessage("&7 &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m &m".replace("&", "§"), ChatType.SUCCESS);

                    } catch (Exception e){
                        Main.log(Level.ERROR, String.valueOf(e));
                        ChatUtil.sendMessage("Could not find that player.", ChatType.FAIL);

                    }


                } else {
                    ChatUtil.sendMessage("Usage: ?profile <player>", ChatType.FAIL);
                }

            } else if (string.startsWith("?copytxt")) {
                Main.MC.keyboard.setClipboard(String.join(" ", args).replace("?copytxt ", ""));
                ChatUtil.sendMessage("Copied text: " + String.join(" ", args).replace("?copytxt ", ""), ChatType.SUCCESS);

            } else {
                ChatUtil.sendMessage("Unknown command! Use ?help for a list of commands.", ChatType.FAIL);
            }

        }).start();
    }

}
