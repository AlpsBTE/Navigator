package com.alpsbte.navigator.utils;

import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class Items {
    public static ItemStack websiteHead;
    public static ItemStack discordHead;

    public static ItemStack getDiscordItem() {
        return new ItemBuilder(discordHead)
                .setName("§b§lDiscord §7(Right Click)")
                .setLore(new LoreBuilder()
                        .description(Collections.singletonList("Join our Discord server for more information."))
                        .build())
                .build();
    }
}
