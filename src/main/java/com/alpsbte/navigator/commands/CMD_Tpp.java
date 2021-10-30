package com.alpsbte.navigator.commands;

import com.alpsbte.navigator.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Tpp implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.tpp")) {
                Player player = (Player)sender;
                try {
                    Player targetPlayer = player.getServer().getPlayer(args[0]);
                    player.teleport(targetPlayer);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1f, 1f);
                    player.sendMessage(Utils.getInfoMessageFormat("Teleporting to player..."));
                } catch (Exception ignore) {
                    player.sendMessage(Utils.getErrorMessageFormat("Usage: /tpp <Player>"));
                }
            } else {
                sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to execute this command!"));
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "This command can only be used as a player!");
        }
        return true;
    }
}
