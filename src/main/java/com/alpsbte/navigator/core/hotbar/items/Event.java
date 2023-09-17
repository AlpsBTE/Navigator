package com.alpsbte.navigator.core.hotbar.items;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.navigator.utils.config.ConfigPaths;
import com.alpsbte.navigator.utils.ServerLoreBuilder;
import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.core.hotbar.NavigatorItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Event extends NavigatorItem {

    @Override
    public Material getMaterial() {
        return Material.valueOf(config.getString(ConfigPaths.SERVERS_EVENT_TYPE_MATERIAL));
    }

    @Override
    public String getTitle() {
        return config.getString(ConfigPaths.SERVERS_EVENT_TYPE_TITLE) + " §7(Click)";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(config.getString(ConfigPaths.SERVERS_EVENT_TYPE_DESCRIPTION).split("/"));
    }

    @Override
    public List<String> getFeatures() {
        return new ArrayList<>(Arrays.asList(
                "§bStart: §f " + config.getString(ConfigPaths.SERVERS_EVENT_TYPE_START_DATE),
                "§bEnd: §f " + config.getString(ConfigPaths.SERVERS_EVENT_TYPE_END_DATE)
        ));
    }

    @Override
    public String getIP() {
        return config.getString(ConfigPaths.SERVERS_EVENT_IP);
    }

    @Override
    public int getPort() {
        return config.getInt(ConfigPaths.SERVERS_EVENT_PORT);
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
                        .server(isServerOnline, NavigatorPlugin.getPlugin().playerCountEVENT)
                        .emptyLine()
                        .version(getVersion(), isModded())
                        .build())
                .setEnchanted(true)
                .build();
    }

    public static ItemStack getItem() {
        return new Event().createItem();
    }
}
