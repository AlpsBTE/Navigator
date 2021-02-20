package github.AlpsBTE_Navigator.commands;

import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import github.AlpsBTE_Navigator.core.navigator.items.Event;
import github.AlpsBTE_Navigator.utils.Utils;
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

                FileConfiguration config = AlpsBTE_Navigator.getPlugin().getConfig();

                Event event = new Event();

                if(AlpsBTE_Navigator.getPlugin().checkServer(event.getIP(), event.getPort())) {
                    if(config.getBoolean("servers.event.joinable") || sender.hasPermission("alpsbte.joinEventStaff")) {
                        sender.sendMessage(Utils.getInfoMessageFormat("Connecting to server"));
                        AlpsBTE_Navigator.getPlugin().connectPlayer((Player)sender, "EVENT");
                    } else {
                        sender.sendMessage(Utils.getErrorMessageFormat("You don't have permission to join the server."));
                    }
                } else {
                    sender.sendMessage(Utils.getErrorMessageFormat("Server is offline"));
                }

            }
        }
        return true;
    }
}
