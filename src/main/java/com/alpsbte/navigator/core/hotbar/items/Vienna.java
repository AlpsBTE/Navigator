package com.alpsbte.navigator.core.hotbar.items;

import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.core.config.ConfigPaths;
import com.alpsbte.navigator.core.hotbar.NavigatorItem;
import com.alpsbte.navigator.utils.ItemBuilder;
import com.alpsbte.navigator.utils.LoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Vienna extends NavigatorItem {
    @Override
    public Material getMaterial() {
        return Material.MAGMA;
    }

    @Override
    public String getTitle() {
        return "§b§lVIENNA 1.18 §c(NEW)";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "Experience the BuildTheEarth project on a whole new level",
                "by using version 1.18+ for building."
        );
    }

    @Override
    public List<String> getFeatures() {
        return null;
    }

    @Override
    public String getIP() {
        return config.getString(ConfigPaths.SERVERS_VIENNA_IP);
    }

    @Override
    public int getPort() {
        return config.getInt(ConfigPaths.SERVERS_VIENNA_PORT);
    }

    @Override
    public String getVersion() {
        return "1.18 - 1.19";
    }

    @Override
    public boolean isModded() {
        return false;
    }

    @Override
    public ItemStack createItem() {
        return new ItemBuilder(getMaterial(), 1)
                .setName(getTitle())
                .setLore(new LoreBuilder()
                        .description(getDescription())
                        .emptyLine()
                        .server(serverIsOnline, NavigatorPlugin.getPlugin().playerCountVIENNA)
                        .emptyLine()
                        .version(getVersion(), isModded())
                        .build())
                .build();
    }
}
