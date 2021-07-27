package io.github.discstudiosclient.features.tab;


import java.net.URI;

import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.util.ILoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Level;

public class Client implements ILoader {

    public static DiscStudiosServer client;

    @Override
    public void load() {
        connect();
    }

    public static void connect() {
        try {
            MinecraftClient mc = Main.MC;
            Session session = mc.getSession();

            String serverid = RandomStringUtils.randomAlphabetic(20);
            mc.getSessionService().joinServer(session.getProfile(), session.getAccessToken(), serverid);
            String url = "wss://DS-Client-Server.techstreetdev.repl.co/?username=" + session.getUsername() + "&serverid=" + serverid + "&version=" + Main.MOD_VERSION + (Main.BETA ? "-BETA" : "");

            client = new DiscStudiosServer(new URI(url));
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}