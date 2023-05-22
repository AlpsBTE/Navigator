package com.alpsbte.navigator.core.hotbar.items.simplified;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import com.alpsbte.navigator.core.hotbar.items.Vienna;
import com.alpsbte.navigator.utils.ServerLoreBuilder;
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
                .setLore(new ServerLoreBuilder()
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
