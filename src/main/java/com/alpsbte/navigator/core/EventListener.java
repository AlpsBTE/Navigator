package com.alpsbte.navigator.core;

import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.utils.config.ConfigPaths;
import com.alpsbte.navigator.core.hotbar.NavigatorMenu;
import com.alpsbte.navigator.core.hotbar.items.simplified.PlotSimplified;
import com.alpsbte.navigator.core.hotbar.items.simplified.TerraSimplified;
import com.alpsbte.navigator.core.hotbar.items.simplified.ViennaSimplified;
import com.alpsbte.navigator.utils.Items;
import com.alpsbte.navigator.utils.Utils;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.getPlayer().teleport(Utils.getSpawnLocation());

        Bukkit.getScheduler().runTaskAsynchronously(NavigatorPlugin.getPlugin(), () -> {
            FileConfiguration config = NavigatorPlugin.getPlugin().getConfig();

            if (NavigatorPlugin.getLuckPerms() != null && !event.getPlayer().hasPermission("group.builder")) {
                ItemStack terraSimplifiedItem = TerraSimplified.getItem();
                ItemStack plotSimplifiedItem = PlotSimplified.getItem();
                ItemStack viennaSimplifiedItem = ViennaSimplified.getItem();

                if (!event.getPlayer().getInventory().contains(terraSimplifiedItem)) {
                    event.getPlayer().getInventory().setItem(0, terraSimplifiedItem);
                }

                if (!event.getPlayer().getInventory().contains(plotSimplifiedItem)) {
                    event.getPlayer().getInventory().setItem(1, plotSimplifiedItem);
                }

                if (!event.getPlayer().getInventory().contains(viennaSimplifiedItem)) {
                    event.getPlayer().getInventory().setItem(2, viennaSimplifiedItem);
                }

                if (!event.getPlayer().getInventory().contains(Items.getDiscordItem())) {
                    event.getPlayer().getInventory().setItem(8, Items.getDiscordItem());
                }
            } else if(!event.getPlayer().getInventory().contains(NavigatorMenu.getItem())) {
                event.getPlayer().getInventory().setItem(0, NavigatorMenu.getItem());
                event.getPlayer().getInventory().setItem(1, null);
                event.getPlayer().getInventory().setItem(2, null);
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
            }*/
        });
    }

    @EventHandler
    public void onPlayerClickEvent(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if(event.getItem() != null) {
                if (event.getItem().equals(NavigatorMenu.getItem())) {
                    NavigatorPlugin.getPlugin().updatePlayerCount(event.getPlayer());
                } else {
                    TerraSimplified terra = new TerraSimplified();
                    ViennaSimplified vienna = new ViennaSimplified();
                    if (event.getItem().equals(TerraSimplified.getItem())) {
                        if (terra.isServerOnline) {
                            event.getPlayer().sendMessage(Utils.getInfoMessageFormat("Connecting to server"));
                            NavigatorPlugin.getPlugin().connectPlayer(event.getPlayer(), Utils.TERRA_SERVER);
                        } else {
                            event.getPlayer().sendMessage(Utils.getErrorMessageFormat("Server is offline"));
                        }
                    } else if(event.getItem().equals(ViennaSimplified.getItem())) {
                        if (vienna.isServerOnline) {
                            event.getPlayer().sendMessage(Utils.getInfoMessageFormat("Connecting to server"));
                            NavigatorPlugin.getPlugin().connectPlayer(event.getPlayer(), Utils.VIENNA_SERVER);
                        } else {
                            event.getPlayer().sendMessage(Utils.getErrorMessageFormat("Server is offline"));
                        }
                    } else {
                        if (event.getItem().equals(PlotSimplified.getItem())) {
                            event.getPlayer().performCommand("companion");
                        } else {
                            if (event.getItem().equals(Items.getDiscordItem())) {
                                TextComponent message = new TextComponent(new TextComponent(ChatColor.GRAY + "[" + ChatColor.GOLD + "Join the Discord" + ChatColor.GRAY + "] >" + ChatColor.GREEN + " https://discord.gg/vgkspay"));
                                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/vgkspay"));
                                event.getPlayer().spigot().sendMessage(message);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if(event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
        int slot = event.getSlot();
        ItemStack item = event.getWhoClicked().getInventory().getItem(slot);
        if (item != null) {
            if (item.equals(NavigatorMenu.getItem()) || item.equals(TerraSimplified.getItem()) || item.equals(PlotSimplified.getItem()) || item.equals(Items.getDiscordItem()) || item.equals(ViennaSimplified.getItem())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerItemDropEvent(PlayerDropItemEvent event) {
        if(event.getItemDrop() != null) {
            if (event.getItemDrop().getItemStack().equals(NavigatorMenu.getItem()) || event.getItemDrop().getItemStack().equals(PlotSimplified.getItem()) || event.getItemDrop().getItemStack().equals(TerraSimplified.getItem()) || event.getItemDrop().getItemStack().equals(Items.getDiscordItem()) || event.getItemDrop().getItemStack().equals(PlotSimplified.getItem())) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onDatabaseLoad(DatabaseLoadEvent e) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        try {
            Items.discordHead = api.getItemHead("29829");
        } catch (NullPointerException ex) {
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
