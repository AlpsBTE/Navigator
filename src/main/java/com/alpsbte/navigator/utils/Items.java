package com.alpsbte.navigator.utils;

import com.alpsbte.alpslib.utils.item.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class Items {
    public static ItemStack discordHead;

    public static ItemStack getDiscordItem() {
        return new ItemBuilder(discordHead)
                .setName("§b§lDiscord §7(Right Click)")
                .setLore(new ServerLoreBuilder()
                        .description(Collections.singletonList("Join our Discord server for more information."))
                        .build())
                .build();
    }
}
