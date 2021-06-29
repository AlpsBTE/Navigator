package github.AlpsBTE_Navigator.utils;

import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Utils {

    // Player Messages
    private static final String messagePrefix = "§7§l>> ";

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

    public final static String TEST_SERVER = "ALPS-4";

    // Spawn Location
    public static Location getSpawnPoint() {
        FileConfiguration config = AlpsBTE_Navigator.getPlugin().getPlotSystemConfig();

        return new Location(Bukkit.getWorld(config.getString("lobby-world")),
                config.getDouble("spawn-point.x"),
                config.getDouble("spawn-point.y"),
                config.getDouble("spawn-point.z"),
                (float) config.getDouble("spawn-point.yaw"),
                (float) config.getDouble("spawn-point.pitch")
        );
    }
}
