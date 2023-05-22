package com.alpsbte.navigator.core.holograms;

import com.alpsbte.alpslib.hologram.HolographicDisplay;
import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.utils.config.ConfigPaths;
import com.alpsbte.navigator.utils.Utils;
import me.filoghost.holographicdisplays.api.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HologramManager {
    private HologramManager() {}

    private static final List<HolographicDisplay> holograms = Arrays.asList(
            new EventInfoHologram("event-info"),
            new SpeedJnRLeaderboard("speed-jnr-leaderboard"),
            new AccuracyJnRLeaderboard("accuracy-jnr-leaderboard")
    );

    public static void reloadHolograms() {
        if (NavigatorPlugin.isHolographicDisplaysEnabled()) {
            for (HolographicDisplay leaderboard : holograms) {
                String path = ConfigPaths.HOLOGRAMS + leaderboard.getId();
                if (NavigatorPlugin.getPlugin().getConfig().getBoolean(path + ConfigPaths.HOLOGRAMS_ENABLED))
                    leaderboard.create(getPosition(leaderboard.getId()));
            }
        }
    }

    public static Position getPosition(String id) {
        FileConfiguration config = NavigatorPlugin.getPlugin().getConfig();
        String path = ConfigPaths.HOLOGRAMS + id;

        return Position.of(Utils.getSpawnLocation().getWorld().getName(),
                config.getDouble(path + ConfigPaths.HOLOGRAMS_X),
                config.getDouble(path + ConfigPaths.HOLOGRAMS_Y),
                config.getDouble(path + ConfigPaths.HOLOGRAMS_Z)
        );
    }

    public static void savePosition(String id, Location newLocation) {
        FileConfiguration config = NavigatorPlugin.getPlugin().getConfig();
        String path = ConfigPaths.HOLOGRAMS + id;

        config.set(path + ConfigPaths.HOLOGRAMS_ENABLED, true);
        config.set(path + ConfigPaths.HOLOGRAMS_X, newLocation.getX());
        config.set(path + ConfigPaths.HOLOGRAMS_Y, newLocation.getY() + 4);
        config.set(path + ConfigPaths.HOLOGRAMS_Z, newLocation.getZ());
        NavigatorPlugin.getPlugin().saveConfig();

        reloadHolograms();
    }

    public static class LeaderboardPositionLine extends HolographicDisplay.TextLine {
        public LeaderboardPositionLine(int position, String username, String time) {
            super("§e#" + position + " " + (username != null ? "§a" + username : "§8No one, yet") + " §7- §b" + time);
        }
    }

    public static List<HolographicDisplay.DataLine<?>> getLeaderboardContent(String jnrName) {
        FileConfiguration parkourConfig = NavigatorPlugin.getPlugin().getLeaderboardConfig();

        Map<String, Integer> parkourScoreMap = new HashMap<>();
        for (String uuid : parkourConfig.getConfigurationSection("History").getKeys(false)) {
            int score = 0;
            try {
                for (String item : parkourConfig.getConfigurationSection("History." + uuid + "." + jnrName).getKeys(false)) {
                    score += parkourConfig.getInt("History." + uuid + "." + jnrName + "." + item);
                }

                String playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
                parkourScoreMap.put((playerName == null ? "Player" : playerName), score);
            } catch (NullPointerException ignore) {}
        }

        List<Map.Entry<String, Integer>> parkourScoreEntries = new ArrayList<>(parkourScoreMap.entrySet());
        parkourScoreEntries.sort(Map.Entry.comparingByValue());

        List<HolographicDisplay.DataLine<?>> dataLines = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (parkourScoreEntries.size() > i) {
                String user = parkourScoreEntries.get(i).getKey();
                Integer time = parkourScoreEntries.get(i).getValue();

                Date date = new Date(time);
                DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateFormatted = formatter.format(date);

                dataLines.add(new HologramManager.LeaderboardPositionLine(i + 1, user, dateFormatted));
            } else dataLines.add(new HologramManager.LeaderboardPositionLine(i + 1, null, "00:00:000"));
        }
        return dataLines;
    }

    public static List<HolographicDisplay> getHolograms() {
        return holograms;
    }
}
