package io.github.discstudiosclient.gui.widget;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CItem extends WItem {

    private Text[] texts = new Text[0];

    public CItem(ItemStack stack) {
        super(stack);
        setTooltip(stack.getTooltip(null, TooltipContext.Default.NORMAL).toArray(new Text[0]));
    }

    @Override
    public void addTooltip(TooltipBuilder tooltip) {
        tooltip.add(texts);
    }

    public void setTooltip(Text... text) {
        texts = text;
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        if (screen != null) {
            if (y > screen.height ||
                    x > screen.width ||
                    x < 0 || y < 0) {
                return;
            }
        }

        super.paint(matrices, x, y, mouseX, mouseY);
    }
}