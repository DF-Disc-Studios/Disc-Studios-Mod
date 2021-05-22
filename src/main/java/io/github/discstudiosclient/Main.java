package io.github.discstudiosclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.discstudiosclient.event.ChatRecievedEvent;
import io.github.discstudiosclient.util.ChatType;
import io.github.discstudiosclient.util.ChatUtil;
import io.github.discstudiosclient.util.TextUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Main implements ModInitializer {
    public static final String MOD_ID = "discstudiosclient";
    public static final String MOD_NAME = "Disc Studios Client";
    public static final String MOD_VERSION = "1.0.0";

    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();
    public byte tickCounter;

    public static boolean DISPLAY_TEXT = false;
    public static String TEXT = "none";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing v" + MOD_VERSION);

        ClientTickEvents.END_CLIENT_TICK.register((listener) -> {

            tickCounter += 1;

            if (tickCounter == 20) {
                new Thread(() -> {
                    Main.MC.player.sendChatMessage("/locate");
                    ChatRecievedEvent.locateParser = true;

                    Integer index = 0;
                    Boolean sucsess = false;
                    while (index != 5000) {
                        index += 1;
                        if (index == 2000) {
                            ChatRecievedEvent.locateParser = false;
                            sucsess = false;
                            break;
                        }

                        if (ChatRecievedEvent.locateParser == false) {
                            sucsess = true;
                            break;
                        }

                        try {
                            Thread.sleep(1);

                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }
                    }

                    if (sucsess) {

                    }

                }).start();
            }
        });
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

}