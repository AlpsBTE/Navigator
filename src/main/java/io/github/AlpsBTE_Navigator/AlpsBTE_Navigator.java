package github.AlpsBTE_Navigator;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import github.AlpsBTE_Navigator.commands.CMD_Event;
import github.AlpsBTE_Navigator.commands.CMD_Navigator;
import github.AlpsBTE_Navigator.commands.CMD_Reload;
import github.AlpsBTE_Navigator.core.EventListener;
import github.AlpsBTE_Navigator.core.navigator.NavigatorMenu;
import github.AlpsBTE_Navigator.utils.PortalManager;
import github.AlpsBTE_Navigator.utils.Utils;
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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;

public class AlpsBTE_Navigator extends JavaPlugin implements PluginMessageListener {

    private static AlpsBTE_Navigator plugin;

    private FileConfiguration config;
    private FileConfiguration plotSystemConfig;
    private File configFile;

    public int playerCountPLOT = 0;
    public int playerCountTERRA = 0;
    public int playerCountEVENT = 0;

    @Override
    public void onEnable() {
        plugin = this;

        reloadConfig();

        // Add Listeners
        this.getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        this.getServer().getPluginManager().registerEvents(new MenuFunctionListener(), plugin);

        // Add Commands
        this.getCommand("event").setExecutor(new CMD_Event());
        this.getCommand("navigator").setExecutor(new CMD_Navigator());
        this.getCommand("nreload").setExecutor(new CMD_Reload());


        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

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

    public static AlpsBTE_Navigator getPlugin() {
        return plugin;
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
        } catch (IOException ex) {
        }
        return false;
    }

    @Override
    public void reloadConfig() {
        configFile = new File(getDataFolder(), "config.yml");
        if (configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
        } else {
            // Look for default configuration file
            try {
                Reader defConfigStream = new InputStreamReader(this.getResource("defaultConfig.yml"), "UTF8");

                config = YamlConfiguration.loadConfiguration(defConfigStream);
            } catch (IOException ex) {
                getLogger().log(Level.SEVERE, "Could not load default configuration file", ex);
            }
        }
        saveConfig();
    }

    @Override
    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public FileConfiguration getPlotSystemConfig() {
        try{
            plotSystemConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getPluginManager().getPlugin("AlpsBTE-PlotSystem").getDataFolder(), "config.yml"));
        } catch (Exception ex){
            Bukkit.getLogger().log(Level.SEVERE, "An error occurred while reading config file!", ex);
        }
        return plotSystemConfig;
    }

    @Override
    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }

        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

}
