package io.github.discstudiosclient.util;

public class Info {

    public static modes CURRENT_MODE;

    public enum modes {
        DEV("Dev"),
        BUILD("Build"),
        PLAY("Play"),
        SPAWN("Spawn"),
        UNKNOWN("Unknown");

        private final String identifier;

        modes(String identifier) {
            this.identifier = identifier;
        }

        public String getIdentifier() {
            return identifier;
        }
    }
}
