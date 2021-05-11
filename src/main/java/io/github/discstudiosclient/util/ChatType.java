package io.github.discstudiosclient.util;


public enum ChatType {
    SUCCESS("§8[§b\uD83E\uDE93§8] §a§l»", '7'),
    FAIL("§8[§b\uD83E\uDE93§8] §cError:", '7');

    private final String prefix;
    private final char trailing;

    ChatType(String prefix, char trailing) {
        this.prefix = prefix;
        this.trailing = trailing;
    }

    public String getString() {
        return this.prefix;
    }

    public char getTrailing() {
        return trailing;
    }
}