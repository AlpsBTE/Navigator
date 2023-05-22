package com.alpsbte.navigator.core.holograms;

import com.alpsbte.alpslib.hologram.HolographicDisplay;
import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.utils.config.ConfigPaths;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EventInfoHologram extends HolographicDisplay {
    protected EventInfoHologram(String id) {
        super(id);
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public String getTitle() {
        return NavigatorPlugin.getPlugin().getConfig().getBoolean(ConfigPaths.SERVERS_EVENT_VISIBLE) ?
                NavigatorPlugin.getPlugin().getConfig().getString(ConfigPaths.SERVERS_EVENT_TYPE_TITLE) : "§6§lEVENT-SERVER";
    }

    @Override
    public List<DataLine<?>> getHeader() {
        return Arrays.asList(
                new TextLine(getTitle()),
                new TextLine("§r")
        );
    }

    @Override
    public List<DataLine<?>> getContent() {
        if (!NavigatorPlugin.getPlugin().getConfig().getBoolean(ConfigPaths.SERVERS_EVENT_VISIBLE)) {
            return Collections.singletonList(new TextLine("§2There is currently no event..."));
        } else {
            List<String> lines = Arrays.asList(NavigatorPlugin.getPlugin().getConfig().getString(ConfigPaths.SERVERS_EVENT_TYPE_DESCRIPTION).split("\\\\n"));
            List<DataLine<?>> dataLines = new ArrayList<>();
            lines.forEach(line -> dataLines.add(new TextLine(line.replace("\\n", ""))));
            return dataLines;
        }
    }

    @Override
    public List<DataLine<?>> getFooter() {
        return Collections.singletonList(new TextLine("§r"));
    }
}
