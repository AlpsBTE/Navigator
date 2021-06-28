package github.AlpsBTE_Navigator.utils;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.logging.Level;

public class PortalManager extends Thread {

    FileConfiguration config;
    World portalWorld;

    // Portals
    public Region Portal_Plot = new CuboidRegion(Vector.toBlockPoint(553, 79, 542), Vector.toBlockPoint(551, 79, 544));
    public Region Portal_Terra = new CuboidRegion(Vector.toBlockPoint(544, 79, 535), Vector.toBlockPoint(542, 79, 533));
    public Region Portal_Event = new CuboidRegion(Vector.toBlockPoint(540, 79, 544), Vector.toBlockPoint(542, 79, 546));

    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(AlpsBTE_Navigator.getPlugin(), () -> {
            config = AlpsBTE_Navigator.getPlugin().getConfig();
            portalWorld = Bukkit.getWorld(config.getString("portals.world"));

            spawnPlotParticles(Portal_Plot.getMinimumPoint(), Portal_Plot.getMaximumPoint());
            spawnPlotParticles(Portal_Terra.getMinimumPoint(), Portal_Terra.getMaximumPoint());
            spawnPlotParticles(Portal_Event.getMinimumPoint(), Portal_Event.getMaximumPoint());

            for(Player player : Bukkit.getOnlinePlayers()) {
                try {
                    Vector playerLocation = Vector.toBlockPoint(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                    if(Portal_Plot.contains(playerLocation)) {
                        player.performCommand("spawn");
                        player.performCommand("companion");
                    } else if(Portal_Terra.contains(playerLocation)) {
                        AlpsBTE_Navigator.getPlugin().connectPlayer(player, Utils.TERRA_SERVER);
                    } else if(Portal_Event.contains(playerLocation)) {
                        if(config.getBoolean("servers.event.joinable") || player.hasPermission("alpsbte.joinEventStaff")) {
                            if(player.hasPermission("alpsbte.joinEvent")) {
                                AlpsBTE_Navigator.getPlugin().connectPlayer(player, Utils.EVENT_SERVER);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Bukkit.getLogger().log(Level.SEVERE, "An error occurred while checking for players in portals.");
                }
            }
        }, 1, 15);
    }

    public void spawnPlotParticles(Vector min, Vector max) {
        for (int i = min.getBlockX(); i <= max.getBlockX();i++) {
            for (int j = min.getBlockY(); j <= max.getBlockY(); j++) {
                for (int k = min.getBlockZ(); k <= max.getBlockZ();k++) {
                    new ParticleBuilder(ParticleEffect.CLOUD, new Location(portalWorld, i, j, k))
                            .setOffsetX(0.5f)
                            .setOffsetZ(0.5f)
                            .setSpeed(0.05f)
                            .display();
                }
            }
        }
    }

}
