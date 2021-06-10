package io.github.discstudiosclient.gui.widget;

import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.util.Info;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

public class CCmdItem extends CItem {

    public CCmdItem(ItemStack stack) {
        super(stack);
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        MinecraftClient mc = MinecraftClient.getInstance();

        mc.player.sendChatMessage(Info.getCmds().get(Main.ITEMS.indexOf(getItems().get(0))));
        return null;
    }
}