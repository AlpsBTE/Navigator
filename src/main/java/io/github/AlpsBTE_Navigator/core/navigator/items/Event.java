package github.AlpsBTE_Navigator.core.navigator.items;

import github.AlpsBTE_Navigator.AlpsBTE_Navigator;
import github.AlpsBTE_Navigator.core.navigator.NavigatorItem;
import github.AlpsBTE_Navigator.utils.ItemBuilder;
import github.AlpsBTE_Navigator.utils.LoreBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Event extends NavigatorItem {

    private final static String typePath = "servers.event.type.";

    @Override
    public Material getMaterial() {
        return Material.valueOf(config.getString(typePath + "material"));
    }

    @Override
    public String getTitle() {
        return config.getString(typePath + "title");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(config.getString(typePath + "description").split("/"));
    }

    @Override
    public List<String> getFeatures() {
        return new ArrayList<>(Arrays.asList(
                "§bStart: §f " + config.getString(typePath + "startDate"),
                "§bEnd: §f " + config.getString(typePath + "endDate")
        ));
    }

    @Override
    public String getIP() {
        return config.getString("servers.event.IP");
    }

    @Override
    public int getPort() {
        return config.getInt("servers.event.port");
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
                        .server(serverIsOnline, AlpsBTE_Navigator.getPlugin().playerCountEVENT)
                        .emptyLine()
                        .version(getVersion(), isModded())
                        .build())
                .setEnchantment(Enchantment.ARROW_DAMAGE)
                .setItemFlag(ItemFlag.HIDE_ENCHANTS)
                .build();
    }

    public static ItemStack getItem() {
        return new Event().createItem();
    }
}
