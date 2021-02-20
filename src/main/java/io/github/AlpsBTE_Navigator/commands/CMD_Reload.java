package github.AlpsBTE_Navigator.commands;

import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import github.AlpsBTE_Navigator.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("alpsbte.nreload")) {
                AlpsBTE_Navigator.getPlugin().reloadConfig();
                AlpsBTE_Navigator.getPlugin().saveConfig();

                sender.sendMessage(Utils.getInfoMessageFormat("Successfully reloaded config."));
            }
        }
        return true;
    }
}
