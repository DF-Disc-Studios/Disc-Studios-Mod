package io.github.discstudiosclient;

import io.github.discstudiosclient.event.ChatRecievedEvent;
import io.github.discstudiosclient.util.ChatType;
import io.github.discstudiosclient.util.ChatUtil;
import io.github.discstudiosclient.util.Info;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Main implements ModInitializer {
    public static final String MOD_ID = "discstudiosclient";
    public static final String MOD_NAME = "Disc Studios Mod";
    public static final String MOD_VERSION = "1.0.0";

    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();

    public static Integer tickCounter = 0;
    public static boolean DISPLAY_TEXT = false;
    public static String TEXT = "none";

    public static ArrayList<ItemStack> ITEMS = new ArrayList<ItemStack>();

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing v" + MOD_VERSION);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter += 1;

            if (tickCounter == 40) {
                tickCounter = 0;
                if (MC.getCurrentServerEntry() != null) {
                    if (MC.player != null) {
                        if (!MC.player.isDead()) {
                            if (MC.getCurrentServerEntry().ping <= 1500) {
                                if (MC.getCurrentServerEntry().address.contains("mcdiamondfire.com")) {
                                    new Thread(() -> {
                                        try {
                                            MC.player.sendChatMessage("/locate");

                                            ChatRecievedEvent.locateParser = true;

                                            Integer index = 0;
                                            while (index != 5000) {
                                                index += 1;
                                                if (index == 1500) {
                                                    ChatRecievedEvent.locateParser = false;
                                                    break;
                                                }

                                                if (!ChatRecievedEvent.locateParser) {
                                                    break;
                                                }

                                                Thread.sleep(1);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                        }
                                    }).start();
                                }
                            }
                        }
                    }
                }

            }
        });
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

}