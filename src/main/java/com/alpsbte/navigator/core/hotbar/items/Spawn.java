package com.alpsbte.navigator.core.hotbar.items;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.navigator.utils.config.ConfigPaths;
import com.alpsbte.navigator.core.hotbar.NavigatorItem;
import com.alpsbte.navigator.utils.ServerLoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.Collections;
import java.util.List;

public class Spawn extends NavigatorItem {

    @Override
    public Material getMaterial() {
        return Material.NETHER_STAR;
    }

    @Override
    public String getTitle() {
        return "§6§lSPAWN §7(Click)";
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList("Teleport to the spawn.");
    }

    @Override
    public List<String> getFeatures() {
        return null;
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
        return null;
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
                        .build())
                .build();
    }
}
