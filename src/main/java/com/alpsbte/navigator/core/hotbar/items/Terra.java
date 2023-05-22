package com.alpsbte.navigator.core.hotbar.items;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.utils.config.ConfigPaths;
import com.alpsbte.navigator.utils.ServerLoreBuilder;
import com.alpsbte.navigator.core.hotbar.NavigatorItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.Arrays;
import java.util.List;

public class Terra extends NavigatorItem {

    @Override
    public Material getMaterial() {
        return Material.BRICK;
    }

    @Override
    public String getTitle() {
        return "§b§lTERRA SERVER §7(Click)";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "Official Map (Terra 1:1) using Cubic Chunks.",
                "Discover all three countries and build wherever you want."
        );
    }

    @Override
    public List<String> getFeatures() {
        return Arrays.asList(
                "Open World Using Cubic Chunks",
                "Discover All 3 Countries",
                "Build And Visit Wherever You Want"
        );
    }

    @Override
    public String getIP() {
        return config.getString(ConfigPaths.SERVERS_TERRA_IP);
    }

    @Override
    public int getPort() {
        return config.getInt(ConfigPaths.SERVERS_TERRA_PORT);
    }

    @Override
    public String getVersion() {
        return "1.12.2 - 1.19";
    }

    @Override
    public boolean isModded() {
        return true;
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
                        .server(isServerOnline, NavigatorPlugin.getPlugin().playerCountTERRA)
                        .emptyLine()
                        .version(getVersion(), isModded())
                        .build())
                .build();
    }
}
