package io.github.discstudiosclient.util;

import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    public static String formUUID(String trimmedUUID) throws IllegalArgumentException {
        if (trimmedUUID == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder builder = new StringBuilder(trimmedUUID.trim());
        try {
            builder.insert(20, "-");
            builder.insert(16, "-");
            builder.insert(12, "-");
            builder.insert(8, "-");
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
        return builder.toString();
    }

    public static Text colorCodesToTextComponent(String message) {
        message = "§f" + message;
        Matcher siblings = null;
        String sibling, literalColorCodes = null, color = null, prevcolor = "reset", text;
        String bold = "null", italic = "null", underlined = "null", strikethrough = "null", obfuscated = "null";
        List<String> sections = new java.util.ArrayList<>(Collections.emptyList());
        List<String> literalColorSections = new java.util.ArrayList<>(Collections.emptyList());
        MutableText result = Text.Serializer.fromJson("{\"text\": \"\"}");
        char literalColorCode;
        int lastColorOccurrence;

        Pattern pattern = Pattern.compile("(§x(§[^§]){6}([^§]|§[lomnk])+)|(§([^§]|§[lomnk])+)");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) sections.add(matcher.group());

        for (String section : sections) {
            sibling = section;

            // reset modifiers
            bold = "null";
            italic = "null";
            underlined = "null";
            strikethrough = "null";
            obfuscated = "null";

            // text
            text = sibling.replaceAll("^(§.)+", "");

            // color
            pattern = Pattern.compile("(§x(§([0-f]|r)){6})|(§([0-f]|r))");
            matcher = pattern.matcher(sibling);
            while (matcher.find()) literalColorSections.add(matcher.group());
            literalColorCodes = literalColorSections.get(literalColorSections.size() - 1);
            if (literalColorSections.size() - 1 != 0) {
                literalColorCode = literalColorCodes.charAt(1);
                if (literalColorCode == 'x') color = MinecraftColors.mcToHex(literalColorCodes);
                else
                    color = String.valueOf(Objects.requireNonNull(MinecraftColors.fromCode(literalColorCodes.charAt(1))).getFormatting());
            }

            // modifiers
            lastColorOccurrence = sibling.lastIndexOf(literalColorCodes);
            if (sibling.indexOf("§l") > lastColorOccurrence) bold = "true";
            if (sibling.indexOf("§o") > lastColorOccurrence) italic = "true";
            if (sibling.indexOf("§n") > lastColorOccurrence) underlined = "true";
            if (sibling.indexOf("§m") > lastColorOccurrence) strikethrough = "true";
            if (sibling.indexOf("§k") > lastColorOccurrence) obfuscated = "true";

            // serializer
            assert false;
            if (!text.equals("")) {
                result.append(Text.Serializer.fromJson("{" +
                        "\"text\": \"" + text +
                        "\", \"color\": \"" + color +
                        "\", \"bold\": \"" + bold +
                        "\", \"italic\": \"" + italic +
                        "\", \"underlined\": \"" + underlined +
                        "\", \"strikethrough\": \"" + strikethrough +
                        "\", \"obfuscated\": \"" + obfuscated +
                        "\"}"));
            }
        }
        return result;
    }

}