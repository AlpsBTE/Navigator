package com.alpsbte.navigator.commands;

import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.core.holograms.HolographicDisplay;
import com.alpsbte.navigator.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_SetHologram implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            if (sender.hasPermission("alpsbte.admin")){
                if (args.length == 1) {
                    // Find hologram by name
                    HolographicDisplay hologram = NavigatorPlugin.getHolograms().stream()
                            .filter(holo -> holo.getHologramName().equalsIgnoreCase(args[0]))
                            .findFirst()
                            .orElse(null);

                    // Update hologram location
                    if(hologram != null) {
                        hologram.setLocation(player.getLocation());
                        player.sendMessage(Utils.getInfoMessageFormat("Successfully updated hologram location!"));

                        NavigatorPlugin.reloadHolograms();
                    } else {
                        player.sendMessage(Utils.getErrorMessageFormat("Hologram could not be found!"));
                    }
                } else {
                    player.sendMessage(Utils.getErrorMessageFormat("§lUsage: §c/sethologram <name>"));
                    player.sendMessage("§7------- §6§lHolograms §7-------");
                    for(HolographicDisplay holo : NavigatorPlugin.getHolograms()) {
                        player.sendMessage(" §6> §f" + holo.getHologramName());
                    }
                    player.sendMessage("§7--------------------------");
                }

            }
        }
        return true;
    }
}
