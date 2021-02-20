package github.AlpsBTE_Navigator.commands;

import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Navigator implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.navigator")) {
                AlpsBTE_Navigator.getPlugin().UpdatePlayerCount(((Player) sender).getPlayer());
            }
        }
        return true;
    }
}
