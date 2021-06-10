package io.github.discstudiosclient.event;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.discstudiosclient.Main;
import io.github.discstudiosclient.gui.ServerSelectorGUI;
import io.github.discstudiosclient.util.ChatType;
import io.github.discstudiosclient.util.ChatUtil;
import io.github.discstudiosclient.util.Info;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

public class InventoryOpenEvent {
    public static void onOpen(InventoryS2CPacket packet, CallbackInfo ci) {

        if (packet.getContents().get(0).getName().toString().equals("TextComponent{text='Node 1', siblings=[], style=Style{ color=#7070FF, bold=null, italic=false, underlined=null, strikethrough=null, obfuscated=null, clickEvent=null, hoverEvent=null, insertion=null, font=minecraft:default}}")) {
            if (Main.MC.player.getMainHandStack().getName().toString().equals("TextComponent{text='', siblings=[TextComponent{text='◇ ', siblings=[], style=Style{ color=red, bold=null, italic=null, underlined=null, strikethrough=null, obfuscated=null, clickEvent=null, hoverEvent=null, insertion=null, font=minecraft:default}}, TextComponent{text='Game Menu', siblings=[], style=Style{ color=green, bold=null, italic=null, underlined=null, strikethrough=null, obfuscated=null, clickEvent=null, hoverEvent=null, insertion=null, font=minecraft:default}}, TextComponent{text=' ◇', siblings=[], style=Style{ color=red, bold=null, italic=null, underlined=null, strikethrough=null, obfuscated=null, clickEvent=null, hoverEvent=null, insertion=null, font=minecraft:default}}], style=Style{ color=null, bold=null, italic=false, underlined=null, strikethrough=null, obfuscated=null, clickEvent=null, hoverEvent=null, insertion=null, font=minecraft:default}}")) {
                if (Info.CURRENT_MODE != Info.modes.SPAWN) {
                    return;
                }

                ItemStack item = new ItemStack(Items.STONE);
                List<ItemStack> stack = packet.getContents();

                /*
                stack.set(5, item);




                try {
                    MinecraftClient.getInstance().openScreen(new CottonClientScreen(new ServerSelectorGUI(stack)));
                } catch (Exception e) {
                    ChatUtil.sendMessage(e.toString(), ChatType.FAIL);
                }
                                 */
            }
        }
    }
}