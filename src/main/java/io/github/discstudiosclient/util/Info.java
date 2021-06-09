package io.github.discstudiosclient.util;

import java.util.ArrayList;

public class Info {

    public static modes CURRENT_MODE;
    public static modes CURRENT_NODE;

    public static ArrayList<String> getCmds() {
        ArrayList<String> cmds = new ArrayList<String>();

        cmds.add("/server node1");
        cmds.add("/server node2");
        cmds.add("/server node3");
        cmds.add("/server node4");
        cmds.add("/server node5");
        cmds.add("/join 50020");
        cmds.add("/");
        cmds.add("/");
        cmds.add("/server beta");

        return cmds;
    }

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

    public enum nodes {
        ONE("1"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        BETA("Beta"),
        UNKNOWN("?");

        private final String identifier;

        nodes(String identifier) {
            this.identifier = identifier;
        }

        public String getIdentifier() {
            return identifier;
        }
    }
}
