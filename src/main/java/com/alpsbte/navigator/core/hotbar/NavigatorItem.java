package com.alpsbte.navigator.core.hotbar;

import com.alpsbte.navigator.NavigatorPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public abstract class NavigatorItem {
    protected final FileConfiguration config;
    public final boolean isServerOnline;

    public NavigatorItem() {
        config = NavigatorPlugin.getPlugin().getConfig();
        isServerOnline = NavigatorPlugin.getPlugin().checkServer(getIP(), getPort());
    }

    public abstract Material getMaterial();

    public abstract String getTitle();

    public abstract List<String> getDescription();

    public abstract List<String> getFeatures();

    public abstract String getIP();

    public abstract int getPort();

    public abstract String getVersion();

    public abstract boolean isModded();

    public abstract ItemStack createItem();
}
