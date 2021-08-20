package com.alpsbte.navigator.core.holograms;

import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.core.config.ConfigPaths;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class EventInfoHologram extends HolographicDisplay {
    public EventInfoHologram() {
        super("event-info");
    }

    @Override
    protected String getTitle() {
        return ("§6§lEVENT-SERVER");
    }

    @Override
    protected void insertLines() {
        getHologram().insertTextLine(0, getTitle());
        getHologram().insertTextLine(1, "§r");

        if(NavigatorPlugin.getPlugin().getConfig().getBoolean(ConfigPaths.SERVERS_EVENT_VISIBLE)) {
            List<String> data = getDataLines();
            for(int i = 2; i < data.size() + 2; i++) {
                getHologram().insertTextLine(i, data.get(i - 2));
            }
        } else {
            getHologram().insertTextLine(2, "§2There is currently no event...");
        }
    }

    @Override
    public void updateHologram() {
        if(isPlaced() && getHologram() != null) {
            getHologram().clearLines();
            insertLines();
        }
    }

    @Override
    protected List<String> getDataLines() {
        return Arrays.asList(NavigatorPlugin.getPlugin().getConfig().getString(ConfigPaths.SERVERS_EVENT_TYPE_DESCRIPTION).split("/"));
    }

    @Override
    protected ItemStack getItem() {
        return null;
    }
}
