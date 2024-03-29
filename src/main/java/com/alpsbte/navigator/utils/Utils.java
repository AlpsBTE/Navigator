package com.alpsbte.navigator.utils;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.alpsbte.navigator.NavigatorPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Level;

public class Utils {

    // Player Messages
    public static final String messagePrefix = "§8§l» ";

    public static String getInfoMessageFormat(String info) {
        return messagePrefix + "§a" + info;
    }

    public static String getErrorMessageFormat(String error) {
        return messagePrefix + "§c" + error;
    }

    // Servers
    public final static String PLOT_SERVER = "ALPS-1";

    public final static String TERRA_SERVER = "ALPS-2";

    public final static String EVENT_SERVER = "ALPS-3";

    public final static String VIENNA_SERVER = "ALPS-5";

    // Spawn Location
    public static Location getSpawnLocation() {
        FileConfiguration config = NavigatorPlugin.getPlugin().getPlotSystemConfig();

        if (config != null && !config.getString("spawn-world").equalsIgnoreCase("default")) {
            try {
                MultiverseWorld spawnWorld = NavigatorPlugin.getMultiverseCore().getMVWorldManager().getMVWorld(config.getString("spawn-world"));
                return spawnWorld.getSpawnLocation();
            } catch (Exception ignore) {
                Bukkit.getLogger().log(Level.WARNING, "Could not find spawn-world in multiverse config!");
            }
        }

        return NavigatorPlugin.getMultiverseCore().getMVWorldManager().getSpawnWorld().getSpawnLocation();
    }
}
