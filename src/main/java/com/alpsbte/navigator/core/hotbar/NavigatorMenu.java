package com.alpsbte.navigator.core.hotbar;

import com.alpsbte.navigator.core.config.ConfigPaths;
import com.alpsbte.navigator.core.hotbar.items.*;
import com.alpsbte.navigator.utils.ItemBuilder;
import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.type.ChestMenu;

public class NavigatorMenu {
    public Menu getUI(Player player) {
        Menu navigatorMenu = getMenu();
        FileConfiguration config = NavigatorPlugin.getPlugin().getConfig();

        // Set glass border
        Mask mask = BinaryMask.builder(navigatorMenu)
                .item(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)7).setName(" ").build())
                .pattern("111101111") // First row
                .pattern("110111011") // Second row
                .pattern("111101111").build(); // Third row
        mask.apply(navigatorMenu);



        // Set Spawn item
        navigatorMenu.getSlot(4).setItem(new Spawn().createItem());
        navigatorMenu.getSlot(4).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();

            // Teleport to Spawn
            player.performCommand("spawn");
        });



        // Set Plot System item
        Plot plot = new Plot();
        navigatorMenu.getSlot(11).setItem(plot.createItem());
        navigatorMenu.getSlot(11).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();

            // Open Plot System Companion Menu
            player.performCommand("companion");
        });



        // Set TERRA Server item
        Terra terra = new Terra();
        navigatorMenu.getSlot(15).setItem(terra.createItem());
        navigatorMenu.getSlot(15).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();

            if(terra.serverIsOnline) {
                clickPlayer.sendMessage(Utils.getInfoMessageFormat("Connecting to server"));
                NavigatorPlugin.getPlugin().connectPlayer(clickPlayer, "ALPS-2");
            } else {
                clickPlayer.sendMessage(Utils.getErrorMessageFormat("Server is offline"));
            }
        });



        //Set Event item
        Event event;
        if(!config.getBoolean(ConfigPaths.SERVERS_EVENT_VISIBLE) && !player.hasPermission("alpsbte.joinEventStaff")) {
            event = new DefaultEvent();
        } else if(!config.getBoolean(ConfigPaths.SERVERS_EVENT_VISIBLE)) {
            event = new DefaultEvent();
        } else {
            event = new Event();
        }

        navigatorMenu.getSlot(22).setItem(event.createItem());

        navigatorMenu.getSlot(22).setClickHandler((clickPlayer, clickInformation) -> {
            clickPlayer.closeInventory();
            clickPlayer.performCommand("event");
        });



        return navigatorMenu;
    }

    public static ItemStack getItem() {
        return new ItemBuilder(Material.COMPASS, 1)
                .setName("§b§lNavigator §7(Right Click)")
                .setEnchantment(Enchantment.ARROW_DAMAGE)
                .setItemFlag(ItemFlag.HIDE_ENCHANTS)
                .build();
    }

    public static Menu getMenu() {
        return ChestMenu.builder(3).title("Connect to a server").redraw(true).build();
    }
}
