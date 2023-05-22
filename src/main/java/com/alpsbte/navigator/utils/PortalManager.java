package com.alpsbte.navigator.utils;

import com.alpsbte.navigator.utils.config.ConfigPaths;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.alpsbte.navigator.NavigatorPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.task.TaskManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PortalManager extends Thread {
    World portalWorld;

    // Portals
    public Region Portal_Plot = new CuboidRegion(Vector.toBlockPoint(553, 79, 542), Vector.toBlockPoint(551, 79, 544));
    public Region Portal_Terra = new CuboidRegion(Vector.toBlockPoint(544, 79, 535), Vector.toBlockPoint(542, 79, 533));
    public Region Portal_Event = new CuboidRegion(Vector.toBlockPoint(540, 79, 544), Vector.toBlockPoint(542, 79, 546));

    public void run() {
        portalWorld = Utils.getSpawnLocation().getWorld();

        spawnPortalParticles(Portal_Plot.getMinimumPoint(), Portal_Plot.getMaximumPoint());
        spawnPortalParticles(Portal_Terra.getMinimumPoint(), Portal_Terra.getMaximumPoint());
        spawnPortalParticles(Portal_Event.getMinimumPoint(), Portal_Event.getMaximumPoint());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(NavigatorPlugin.getPlugin(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                try {
                    Vector playerLocation = Vector.toBlockPoint(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                    if (Portal_Plot.contains(playerLocation)) {
                        player.teleport(Utils.getSpawnLocation());
                        player.performCommand("companion");
                    } else if (Portal_Terra.contains(playerLocation)) {
                        NavigatorPlugin.getPlugin().connectPlayer(player, Utils.TERRA_SERVER);
                    } else if (Portal_Event.contains(playerLocation)) {
                        if (NavigatorPlugin.getPlugin().getConfig().getBoolean(ConfigPaths.SERVERS_EVENT_JOINABLE) || player.hasPermission("alpsbte.joinEventStaff")) {
                            if (player.hasPermission("alpsbte.joinEvent")) {
                                NavigatorPlugin.getPlugin().connectPlayer(player, Utils.EVENT_SERVER);
                            }
                        }
                    }
                } catch (Exception ex) {
                    Bukkit.getLogger().log(Level.SEVERE, "An error occurred while checking for players in portals.");
                }
            }
        }, 1, 15);
    }

    public void spawnPortalParticles(Vector min, Vector max) {
        List<Object> packets = new ArrayList<>();
        ParticleBuilder particle = new ParticleBuilder(ParticleEffect.CLOUD).setSpeed(0.05f);
        for (int i = min.getBlockX(); i <= max.getBlockX(); i++) {
            for (int j = min.getBlockY(); j <= max.getBlockY(); j++) {
                for (int k = min.getBlockZ(); k <= max.getBlockZ(); k++) {
                    packets.add(particle.setLocation(new Location(portalWorld, i + 0.5, j, k + 0.5)).toPacket());
                }
            }
        }
        TaskManager.startGlobalTask(packets, 4);
    }
}
