package io.github.discstudiosclient.util;

import io.github.discstudiosclient.Main;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.*;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ChatUtil {

    public static void sendMessage(String text) {
        sendMessage(new LiteralText(text), null);
    }

    public static void sendMessage(String text, ChatType prefixType) {
        sendMessage(new LiteralText(text), prefixType);
    }

    public static void sendTranslateMessage(String identifier, ChatType prefixType) {
        sendMessage(new TranslatableText(identifier), prefixType);
    }

    public static void sendMessage(TranslatableText component, ChatType prefixType) {
        sendMessage((BaseText) component, prefixType);
    }

    public static void sendMessage(MutableText text, @Nullable ChatType chatType) {
        ClientPlayerEntity player = Main.MC.player;
        if (player == null) {
            return;
        }

        if (chatType == null) {
            player.sendMessage(text, false);
        } else {
            MinecraftColors minecraftColors = MinecraftColors.fromCode(chatType.getTrailing());
            if (minecraftColors == null) {
                minecraftColors = MinecraftColors.RED;
            }

            ChatUtil.setColor(text, minecraftColors.getColor());
            player.sendMessage(new LiteralText(chatType.getString() + " ").append(text), false);
            if (chatType == ChatType.FAIL) {
                player.playSound(SoundEvents.ENTITY_SHULKER_HURT_CLOSED, SoundCategory.MASTER, 2, 1);
            }
        }
    }

    public static MutableText setColor(MutableText component, Color color) {
        Style colorStyle = component.getStyle().withColor(TextColor.fromRgb(color.getRGB()));
        component.setStyle(colorStyle);
        return component;
    }

}