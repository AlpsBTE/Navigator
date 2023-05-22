package com.alpsbte.navigator.utils;

import java.util.ArrayList;
import java.util.List;

public class ServerLoreBuilder {
    private final List<String> lore = new ArrayList<>();

    public ServerLoreBuilder description(List<String> lines) {
        for(String line : lines) {
            lore.add("§7" + line);
        }

        return this;
    }

    public ServerLoreBuilder server(boolean available, int players) {
        if(available) {
            lore.add("§a§l>> Connect To Server <<");
            lore.add("§6" + players + " §7currently playing");
        } else {
            lore.add("§c§l>> Server is offline <<");
        }
        return this;
    }

    public ServerLoreBuilder features(List<String> lines) {
        if(lines == null) {
            lore.remove(lore.size() - 1);
        } else

            for(String line : lines) {
                lore.add("§e>> §f" + line);
            }

        return this;
    }

    public ServerLoreBuilder version(String version, boolean modded) {
        if(modded) {
            lore.add("§7Version: §6" + version + " §7| §bModpack Recommended");
        } else {
            lore.add("§7Version: §6" + version);
        }

        return this;
    }

    public ServerLoreBuilder emptyLine() {
        lore.add("");
        return this;
    }

    public List<String> build() {
        return lore;
    }
}
