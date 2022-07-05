package com.alpsbte.navigator.core.hotbar.items.simplified;

import com.alpsbte.navigator.core.hotbar.items.Vienna;
import com.alpsbte.navigator.utils.ItemBuilder;
import com.alpsbte.navigator.utils.LoreBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ViennaSimplified extends Vienna {
    @Override
    public Material getMaterial() {
        return Material.CLAY_BRICK;
    }

    @Override
    public ItemStack createItem() {
        return new ItemBuilder(getMaterial(), 1)
                .setName(getTitle())
                .setLore(new LoreBuilder()
                        .description(getDescription())
                        .emptyLine()
                        .version(getVersion(), isModded())
                        .build())
                .build();
    }

    public static ItemStack getItem() {
        return new ViennaSimplified().createItem();
    }
}
