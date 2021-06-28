package github.AlpsBTE_Navigator.core.navigator.items;

import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import github.AlpsBTE_Navigator.core.navigator.NavigatorItem;
import github.AlpsBTE_Navigator.utils.ItemBuilder;
import github.AlpsBTE_Navigator.utils.LoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.Arrays;
import java.util.List;

public class Plot extends NavigatorItem {

    @Override
    public Material getMaterial() {
        return Material.WORKBENCH;
    }

    @Override
    public String getTitle() {
        return "§b§lPLOT SYSTEM";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "Choose a city, get a outline and start building on your plot.",
                "When you are done, we will review it and add it to the Terra 1:1 world."
        );
    }

    @Override
    public List<String> getFeatures() {
        return Arrays.asList(
                "Vanilla (No Mods)",
                "Easy To Use",
                "Different difficulty levels"
        );
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
        return "1.12.2 - 1.17";
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
                        .features(getFeatures())
                        .emptyLine()
                        .server(serverIsOnline, AlpsBTE_Navigator.getPlugin().playerCountPLOT)
                        .emptyLine()
                        .version(getVersion(), isModded())
                        .build())
                .build();
    }
}
