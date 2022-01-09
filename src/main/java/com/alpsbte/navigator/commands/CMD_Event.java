package com.alpsbte.navigator.commands;

import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.core.config.ConfigPaths;
import com.alpsbte.navigator.core.hotbar.items.Event;
import com.alpsbte.navigator.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CMD_Event implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("alpsbte.joinEvent")) {

                FileConfiguration config = NavigatorPlugin.getPlugin().getConfig();

                Event event = new Event();

                if(NavigatorPlugin.getPlugin().checkServer(event.getIP(), event.getPort())) {
                    if(config.getBoolean(ConfigPaths.SERVERS_EVENT_JOINABLE) || sender.hasPermission("alpsbte.joinEventStaff")) {
                        sender.sendMessage(Utils.getInfoMessageFormat("Connecting to server"));
                        NavigatorPlugin.getPlugin().connectPlayer((Player)sender, "ALPS-3");
                    } else {
                        sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to join the server."));
                    }
                } else {
                    sender.sendMessage(Utils.getErrorMessageFormat("Server is offline"));
                }

            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to join the server."));
            }
        }
        return true;
    }
}
