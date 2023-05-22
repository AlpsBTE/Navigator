package com.alpsbte.navigator.core.hotbar.items.simplified;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.navigator.core.hotbar.items.Terra;
import com.alpsbte.navigator.utils.ServerLoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TerraSimplified extends Terra {

    @Override
    public Material getMaterial() {
        return Material.EYE_OF_ENDER;
    }

    @Override
    public String getTitle() {
        return "§b§lVisit Us §7(Right Click)";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("Discover all three countries by visiting already",
                "built cities, villages and famous places.");
    }

    @Override
    public List<String> getFeatures() {
        return null;
    }

    @Override
    public ItemStack createItem() {
        return new ItemBuilder(getMaterial(), 1)
                .setName(getTitle())
                .setLore(new ServerLoreBuilder()
                        .description(getDescription())
                        .emptyLine()
                        .features(getFeatures())
                        .emptyLine()
                        .version(getVersion(), isModded())
                        .build())
                .build();
    }

    public static ItemStack getItem() {
        return new TerraSimplified().createItem();
    }
}
