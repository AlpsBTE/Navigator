package com.alpsbte.navigator.core.hotbar.items;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.utils.config.ConfigPaths;
import com.alpsbte.navigator.core.hotbar.NavigatorItem;
import com.alpsbte.navigator.utils.ServerLoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.Arrays;
import java.util.List;

public class Plot extends NavigatorItem {

    @Override
    public Material getMaterial() {
        return Material.WORKBENCH;
    }

    @Override
    public String getTitle() {
        return "§b§lPLOT SYSTEM §7(Click)";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "Choose a city, get a outline and start building on your plot.",
                "When you are done, we will review it and add it to the Terra 1:1 world."
        );
    }

    @Override
    public List<String> getFeatures() {
        return Arrays.asList(
                "Vanilla (No Mods)",
                "Easy To Use",
                "Different difficulty levels"
        );
    }

    @Override
    public String getIP() {
        return config.getString(ConfigPaths.SERVERS_PLOT_IP);
    }

    @Override
    public int getPort() {
        return config.getInt(ConfigPaths.SERVERS_PLOT_PORT);
    }

    @Override
    public String getVersion() {
        return "1.12.2 - 1.20.1";
    }

    @Override
    public boolean isModded() {
        return false;
    }

    @Override
    public ItemStack createItem() {
        return new ItemBuilder(getMaterial(), 1)
                .setName(getTitle())
                .setLore(new ServerLoreBuilder()
                        .description(getDescription())
                        .emptyLine()
                        .features(getFeatures())
                        .emptyLine()
                        .server(isServerOnline, NavigatorPlugin.getPlugin().playerCountPLOT)
                        .emptyLine()
                        .version(getVersion(), isModded())
                        .build())
                .build();
    }
}
