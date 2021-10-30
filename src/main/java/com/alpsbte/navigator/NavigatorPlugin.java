package com.alpsbte.navigator;

import com.alpsbte.navigator.commands.*;
import com.alpsbte.navigator.core.config.ConfigManager;
import com.alpsbte.navigator.core.config.ConfigNotImplementedException;
import com.alpsbte.navigator.core.config.ConfigPaths;
import com.alpsbte.navigator.core.holograms.AccuracyJnRLeaderboard;
import com.alpsbte.navigator.core.holograms.EventInfoHologram;
import com.alpsbte.navigator.core.holograms.HolographicDisplay;
import com.alpsbte.navigator.core.holograms.SpeedJnRLeaderboard;
import com.alpsbte.navigator.core.navigator.NavigatorMenu;
import com.alpsbte.navigator.utils.PortalManager;
import com.alpsbte.navigator.utils.Utils;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.alpsbte.navigator.core.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.ipvp.canvas.MenuFunctionListener;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class NavigatorPlugin extends JavaPlugin implements PluginMessageListener {

    private static NavigatorPlugin plugin;
    private static MultiverseCore multiverseCore;

    // Config
    private ConfigManager configManager;
    private FileConfiguration plotSystemConfig;
    private FileConfiguration leaderboardConfig;

    // Holograms
    private static final List<HolographicDisplay> holograms = Arrays.asList(
            new SpeedJnRLeaderboard(),
            new AccuracyJnRLeaderboard(),
            new EventInfoHologram()
    );

    public int playerCountPLOT = 0;
    public int playerCountTERRA = 0;
    public int playerCountEVENT = 0;

    @Override
    public void onEnable() {
        plugin = this;
        multiverseCore = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");

        try {
            configManager = new ConfigManager();
        } catch (ConfigNotImplementedException ex) {
            return;
        }

        reloadConfig();

        // Add Listeners
        this.getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        this.getServer().getPluginManager().registerEvents(new MenuFunctionListener(), plugin);

        // Add Commands
        this.getCommand("event").setExecutor(new CMD_Event());
        this.getCommand("navigator").setExecutor(new CMD_Navigator());
        this.getCommand("tpp").setExecutor(new CMD_Tpp());
        this.getCommand("hub").setExecutor(new CMD_Hub());
        this.getCommand("nreload").setExecutor(new CMD_Reload());
        this.getCommand("sethologram").setExecutor(new CMD_SetHologram());


        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        reloadHolograms();

        new PortalManager().start();

        getLogger().log(Level.INFO, "Successfully enabled AlpsBTE-Navigator plugin.");
    }

    public void UpdatePlayerCount(Player player) {
        new Thread(() -> {
            getCount(player, Utils.PLOT_SERVER);
            getCount(player, Utils.TERRA_SERVER);
            getCount(player, Utils.EVENT_SERVER);

            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    new NavigatorMenu().getUI(player).open(player);
                }
            };
            task.runTaskLater(this, 2);
        }).start();
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel.equals("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subChannel = in.readUTF();
            if (subChannel.equals("PlayerCount")) {
                String server = in.readUTF();

                switch (server.toUpperCase()) {
                    case Utils.PLOT_SERVER:
                        playerCountPLOT = in.readInt();
                        break;
                    case Utils.TERRA_SERVER:
                        playerCountTERRA = in.readInt();
                        break;
                    case Utils.EVENT_SERVER:
                        playerCountEVENT = in.readInt();
                        break;
                }
            }
        }
    }

    private void getCount(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);

        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public void connectPlayer(Player player, String server) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ConnectOther");
            out.writeUTF(player.getName());
            out.writeUTF(server);
            player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Could not connect player [" + player + "] to " + server + "!");
        }
    }

    public boolean checkServer(String IP, int port) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(IP, port), 30); //good timeout is 10-20
            return true;
        } catch (IOException ignored) {}
        return false;
    }

    public static void reloadHolograms() {
        for (HolographicDisplay hologram : holograms) {
            if(getPlugin().getConfig().getBoolean(hologram.getDefaultPath() + ConfigPaths.HOLOGRAMS_ENABLED)) {
                hologram.show();
            } else {
                hologram.hide();
            }
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return this.configManager.getConfig();
    }

    @Override
    public void reloadConfig() {
        this.configManager.reloadConfig();
    }

    @Override
    public void saveConfig() {
        this.configManager.saveConfig();
    }

    public FileConfiguration getLeaderboardConfig() {
        try{
            leaderboardConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getPluginManager().getPlugin("LeakParkour").getDataFolder(), "history.yml"));
        } catch (Exception ex){
            Bukkit.getLogger().log(Level.SEVERE, "An error occurred while reading config file!", ex);
        }
        return leaderboardConfig;
    }

    public FileConfiguration getPlotSystemConfig() {
        try{
            plotSystemConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getPluginManager().getPlugin("Plot-System").getDataFolder(), "config.yml"));
        } catch (Exception ex){
            Bukkit.getLogger().log(Level.SEVERE, "An error occurred while reading config file!", ex);
        }
        return plotSystemConfig;
    }

    public static NavigatorPlugin getPlugin() {
        return plugin;
    }

    public static MultiverseCore getMultiverseCore() { return multiverseCore; }

    public static List<HolographicDisplay> getHolograms() { return holograms; }
}
