package github.AlpsBTE_Navigator.core;

import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import github.AlpsBTE_Navigator.core.navigator.NavigatorMenu;
import github.AlpsBTE_Navigator.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Bukkit.broadcastMessage("§7[§6+§7] > " + event.getPlayer().getName());

        FileConfiguration config = AlpsBTE_Navigator.getPlugin().getConfig();

        if(!event.getPlayer().getInventory().contains(NavigatorMenu.getItem())) {
            event.getPlayer().getInventory().setItem(0, NavigatorMenu.getItem());
        }

        if(config.getBoolean("enableJoinMessage")) {
            String message = config.getString("joinMessage");

            if(message.length() > 0) {
                event.getPlayer().sendMessage(Utils.getInfoMessageFormat(message));
            }
        }

        List<String> builders = config.getStringList("builders");

        for(String builder : builders) {
            if(event.getPlayer().getUniqueId().equals(UUID.fromString(builder))) {
                try {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + event.getPlayer().getName() + " group add builder");
                    config.getStringList("builders").set(config.getStringList("builders").indexOf(builder), null);
                    AlpsBTE_Navigator.getPlugin().saveConfig();
                    return;
                } catch (Exception ex) {
                    AlpsBTE_Navigator.getPlugin().getLogger().log(Level.SEVERE, "Could not add builder permission to player: " + builder, ex);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Bukkit.broadcastMessage("§7[§c-§7] > " + event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerClickEvent(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if(event.getItem() != null && event.getItem().equals(NavigatorMenu.getItem())) {
                AlpsBTE_Navigator.getPlugin().UpdatePlayerCount(event.getPlayer());
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
