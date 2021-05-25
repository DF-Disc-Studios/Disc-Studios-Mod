package io.github.discstudiosclient;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final String MOD_ID = "discstudiosclient";
    public static final String MOD_NAME = "Disc Studios Mod";
    public static final String MOD_VERSION = "1.0.0";

    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final Logger LOGGER = LogManager.getLogger();
    public byte tickCounter;

    public static boolean DISPLAY_TEXT = false;
    public static String TEXT = "none";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing v" + MOD_VERSION);

    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

}