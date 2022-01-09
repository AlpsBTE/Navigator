package com.alpsbte.navigator.core;

import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.core.config.ConfigPaths;
import com.alpsbte.navigator.core.hotbar.NavigatorMenu;
import com.alpsbte.navigator.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.getPlayer().teleport(Utils.getSpawnLocation());

        Bukkit.getScheduler().runTaskAsynchronously(NavigatorPlugin.getPlugin(), () -> {
            FileConfiguration config = NavigatorPlugin.getPlugin().getConfig();

            if(!event.getPlayer().getInventory().contains(NavigatorMenu.getItem())) {
                event.getPlayer().getInventory().setItem(0, NavigatorMenu.getItem());
            }

            if(config.getBoolean(ConfigPaths.SERVERS_PLOT_ENABLE_JOIN_MESSAGE)) {
                String message = config.getString(ConfigPaths.SERVERS_PLOT_JOIN_MESSAGE);

                if(message.length() > 0) {
                    event.getPlayer().sendMessage(Utils.getInfoMessageFormat(message));
                }
            }

            /*List<String> builders = config.getStringList("builders");
            for(String builder : builders) {
                if(event.getPlayer().getUniqueId().equals(UUID.fromString(builder))) {
                    try {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + event.getPlayer().getName() + " group add builder");
                        config.getStringList("builders").set(config.getStringList("builders").indexOf(builder), null);
                        NavigatorPlugin.getPlugin().saveConfig();
                        return;
                    } catch (Exception ex) {
                        NavigatorPlugin.getPlugin().getLogger().log(Level.SEVERE, "Could not add builder permission to player: " + builder, ex);
                    }
                }
            }
        });
    }

    @EventHandler
    public void onPlayerClickEvent(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if(event.getItem() != null && event.getItem().equals(NavigatorMenu.getItem())) {
                NavigatorPlugin.getPlugin().UpdatePlayerCount(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerItemDropEvent(PlayerDropItemEvent event) {
        if(event.getItemDrop() != null && event.getItemDrop().getItemStack().equals(NavigatorMenu.getItem())) {
            event.setCancelled(true);
        }
    }
}
