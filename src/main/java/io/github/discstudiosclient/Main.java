package io.github.discstudiosclient;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final String MOD_ID = "discstudiosclient";
    public static final String MOD_NAME = "Disc Studios Client";
    public static final String MOD_VERSION = "1.0.0";

    public static final Logger LOGGER = LogManager.getLogger();

    private final Identifier df_button = new Identifier(Main.MOD_ID + ":df.png");

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

}