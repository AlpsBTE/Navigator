package com.alpsbte.navigator.commands;

import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.nreload")) {
                NavigatorPlugin.getPlugin().reloadConfig();
                NavigatorPlugin.getPlugin().saveConfig();
                sender.sendMessage(Utils.getInfoMessageFormat("Successfully reloaded config!"));

                NavigatorPlugin.reloadHolograms();
                sender.sendMessage(Utils.getInfoMessageFormat("Successfully reloaded holograms!"));
            }
        }
        return true;
    }
}
