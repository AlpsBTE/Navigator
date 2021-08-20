package com.alpsbte.navigator.core.holograms;

import com.alpsbte.navigator.NavigatorPlugin;
import com.alpsbte.navigator.core.config.ConfigPaths;
import com.alpsbte.navigator.utils.Utils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.logging.Level;

public abstract class HolographicDisplay {

    private final String hologramName;
    private Hologram hologram;
    private boolean isPlaced = false;

    public HolographicDisplay(String hologramName) {
        this.hologramName = hologramName;
    }

    public void show() {
        placeHologram();
        updateHologram();
    }

    public void hide() {
        if(isPlaced()){
            getHologram().delete();
            isPlaced = false;
        }
    }

    public Location getLocation() {
        try {
            FileConfiguration config = NavigatorPlugin.getPlugin().getConfig();

            return new Location(Utils.getSpawnLocation().getWorld(),
                    config.getDouble(getDefaultPath() + ConfigPaths.HOLOGRAMS_X),
                    config.getDouble(getDefaultPath() + ConfigPaths.HOLOGRAMS_Y),
                    config.getDouble(getDefaultPath() + ConfigPaths.HOLOGRAMS_Z)
            );
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not read hologram location of " + getHologramName() + "!", ex);
            return null;
        }
    }

    public void setLocation(Location newLocation) {
        FileConfiguration config = NavigatorPlugin.getPlugin().getConfig();

        config.set(getDefaultPath() + ConfigPaths.HOLOGRAMS_ENABLED, true);
        config.set(getDefaultPath() + ConfigPaths.HOLOGRAMS_X, newLocation.getX());
        config.set(getDefaultPath() + ConfigPaths.HOLOGRAMS_Y, newLocation.getY() + 4);
        config.set(getDefaultPath() + ConfigPaths.HOLOGRAMS_Z, newLocation.getZ());

        NavigatorPlugin.getPlugin().saveConfig();

        if (isPlaced) {
            hologram.delete();
            isPlaced = false;
        }

        placeHologram();
    }

    public void placeHologram() {
        if(!isPlaced() && getLocation() != null) {
            hologram = HologramsAPI.createHologram(NavigatorPlugin.getPlugin(), getLocation());
            isPlaced = true;
        }
    }

    protected void insertLines() {
        getHologram().insertItemLine(0, getItem());

        getHologram().insertTextLine(1, getTitle());
        getHologram().insertTextLine(2, "§7---------------");

        List<String> data = getDataLines();
        for(int i = 2; i < data.size() + 2; i++) {
            getHologram().insertTextLine(i + 1,"§e#" + (i - 1) + " §a" +data.get(i - 2).split(",")[0] + " §7- §b" + data.get(i - 2).split(",")[1]);
        }

        getHologram().insertTextLine(data.size() + 3, "§7---------------");
    }

    protected abstract List<String> getDataLines();

    protected abstract ItemStack getItem();

    public void updateHologram() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(NavigatorPlugin.getPlugin(), () -> {
            if(isPlaced()) {
                hologram.clearLines();
                insertLines();
            }
        },0, getInterval());
    }

    public String getHologramName() { return hologramName; }

    protected abstract String getTitle();

    public Hologram getHologram() { return hologram; }

    public boolean isPlaced() { return isPlaced; }

    public int getInterval() { return 20*60; }

    public String getDefaultPath() {
        return ConfigPaths.HOLOGRAMS + getHologramName();
    }
}
