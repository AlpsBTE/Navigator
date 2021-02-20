package github.AlpsBTE_Navigator.core.navigator.items;

import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import github.AlpsBTE_Navigator.core.navigator.NavigatorItem;
import github.AlpsBTE_Navigator.utils.ItemBuilder;
import github.AlpsBTE_Navigator.utils.LoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.Collections;
import java.util.List;

public class Spawn extends NavigatorItem {

    @Override
    public Material getMaterial() {
        return Material.NETHER_STAR;
    }

    @Override
    public String getTitle() {
        return "§6§lSPAWN";
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList("Teleport to the spawn.");
    }

    @Override
    public List<String> getFeatures() {
        return null;
    }

    @Override
    public String getIP() {
        return config.getString("servers.plot.IP");
    }

    @Override
    public int getPort() {
        return config.getInt("servers.plot.port");
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public boolean isModded() {
        return false;
    }

    @Override
    public ItemStack createItem() {
        return new ItemBuilder(getMaterial(), 1)
                .setName(getTitle())
                .setLore(new LoreBuilder()
                    .description(getDescription())
                    .emptyLine()
                    .server(serverIsOnline, AlpsBTE_Navigator.getPlugin().playerCountPLOT)
                    .build())
                .build();
    }
}
