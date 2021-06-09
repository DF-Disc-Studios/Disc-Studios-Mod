package io.github.discstudiosclient.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.gui.widget.CCmdItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ServerSelectorGUI extends LightweightGuiDescription {
    public ServerSelectorGUI(List<ItemStack> stack) {

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(0, 0);

        int x = 0;

        stack = stack.subList(0, 9);
        ArrayList<ItemStack> newStack = new ArrayList<ItemStack>();
        for (ItemStack itemAdd : stack) {
            newStack.add(itemAdd);
        }

        Main.ITEMS = newStack;

        for (ItemStack item : stack) {

            if (item.getItem().equals(Item.byRawId(0))) {
                WItem i = new WItem(item);
                root.add(i, x, 1);
            } else {
                /*
                item.setCustomName(new LiteralText("§x§d§9§f§f§4§fNode 6"));
                item.getTooltip(Main.MC.player, TooltipContext.Default.NORMAL).add(new LiteralText("§x§d§9§f§f§4§fCaverns"));
                item.getTooltip(Main.MC.player, TooltipContext.Default.NORMAL).add(new LiteralText("§d"));
                item.getTooltip(Main.MC.player, TooltipContext.Default.NORMAL).add(new LiteralText("§8Information cannot be viewed"));
                item.getTooltip(Main.MC.player, TooltipContext.Default.NORMAL).add(new LiteralText("§8from your current node."));
                item.getTooltip(Main.MC.player, TooltipContext.Default.NORMAL).add(new LiteralText("§d"));
                item.getTooltip(Main.MC.player, TooltipContext.Default.NORMAL).add(new LiteralText("§9⇄ Click to connect"));
                                     */

                CCmdItem i = new CCmdItem(item);
                root.add(i, x, 1);
            }
            x += 1;
        }
    }

}