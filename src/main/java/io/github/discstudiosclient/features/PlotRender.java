package io.github.discstudiosclient.features;

import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.event.ChatRecievedEvent;
import io.github.discstudiosclient.util.Info;

public class PlotRender {
    public static void render () {
        if (ChatRecievedEvent.locateParser_id.startsWith("51169") || ChatRecievedEvent.locateParser_id.startsWith("51155")) {
            if (Info.CURRENT_MODE.equals(Info.modes.PLAY)) {
                try {
                    Main.DISPLAY_TEXT = true;
                    Integer i = 0;

                    Integer item_cactuschunk = 0;
                    Integer item_cactuscluster = 0;
                    Integer item_cactusgem = 0;

                    Integer item_gold_cactuschunk = 0;
                    Integer item_gold_cactuscluster = 0;
                    Integer item_gold_cactusgem = 0;

                    Integer item_darkcactus = 0;

                    while (i != 36) {
                        if (Main.MC.player.getInventory().getStack(i).getName().toString().startsWith("TextComponent{text='', siblings=[TextComponent{text='Cactus Chunk'")) {
                            item_cactuschunk += Main.MC.player.getInventory().getStack(i).getCount();
                        }

                        if (Main.MC.player.getInventory().getStack(i).getName().toString().startsWith("TextComponent{text='', siblings=[TextComponent{text='Cactus Cluster'")) {
                            item_cactuscluster += Main.MC.player.getInventory().getStack(i).getCount();
                        }

                        if (Main.MC.player.getInventory().getStack(i).getName().toString().startsWith("TextComponent{text='', siblings=[TextComponent{text='Cactus Gem'")) {
                            item_cactusgem += Main.MC.player.getInventory().getStack(i).getCount();
                        }

                        if (Main.MC.player.getInventory().getStack(i).getName().toString().startsWith("TextComponent{text='', siblings=[TextComponent{text='Golden Cactus'")) {
                            item_gold_cactuschunk += Main.MC.player.getInventory().getStack(i).getCount();
                        }

                        if (Main.MC.player.getInventory().getStack(i).getName().toString().startsWith("TextComponent{text='', siblings=[TextComponent{text='Golden Cactus Cluster'")) {
                            item_gold_cactuscluster += Main.MC.player.getInventory().getStack(i).getCount();
                        }

                        if (Main.MC.player.getInventory().getStack(i).getName().toString().startsWith("TextComponent{text='', siblings=[TextComponent{text='Golden Cactus Gem'")) {
                            item_gold_cactusgem += Main.MC.player.getInventory().getStack(i).getCount();
                        }

                        if (Main.MC.player.getInventory().getStack(i).getName().toString().startsWith("TextComponent{text='', siblings=[TextComponent{text='Black Cactus'")) {
                            item_darkcactus += Main.MC.player.getInventory().getStack(i).getCount();
                        }


                        i += 1;
                    }

                    Main.TEXT = ChatRecievedEvent.locateParser_name +
                            "\n§fTotal: §a$" + Math.round((item_cactusgem * 72) + (item_cactuscluster * 8) + item_cactuschunk + (item_gold_cactusgem * 216) + (item_gold_cactuscluster * 24) + (item_gold_cactuschunk * 3)) +
                            "\n\n§fCactus Gem: §a$" + Math.round(item_cactusgem * 72) +
                            "\n§fCactus Cluster: §a$" + Math.round(item_cactuscluster * 8) +
                            "\n§fCactus Chunk: §a$" + Math.round(item_cactuschunk) +
                            "\n\n§fGolden Cactus Gem: §a$" + Math.round(item_gold_cactusgem * 216) +
                            "\n§fGolden Cactus Cluster: §a$" + Math.round(item_gold_cactuscluster * 24) +
                            "\n§fGolden Cactus: §a$" + Math.round(item_gold_cactuschunk * 3) +
                            "\n\n§fBlack Cactus: §a$" + Math.round(item_darkcactus * 6);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Main.DISPLAY_TEXT = false;

        }
    }
}
