package com.alpsbte.navigator.commands;

import com.alpsbte.navigator.NavigatorPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Navigator implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.navigator")) {
                NavigatorPlugin.getPlugin().UpdatePlayerCount(((Player) sender).getPlayer());
            }
        }
        return true;
    }
}
