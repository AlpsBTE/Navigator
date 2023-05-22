package com.alpsbte.navigator.core.hotbar.items.simplified;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.navigator.core.hotbar.items.Plot;
import com.alpsbte.navigator.utils.ServerLoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PlotSimplified extends Plot {

    @Override
    public Material getMaterial() {
        return Material.MAGMA_CREAM;
    }

    @Override
    public String getTitle() {
        return "§b§lStart Building §7(Right Click)";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "Choose a city, get a outline and start building on your plot.",
                "When you are done, we will review it and add it to the official map.",
                "After completing two Plots, you can apply as builder to build wherever you want."
        );
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
        return new PlotSimplified().createItem();
    }
}
