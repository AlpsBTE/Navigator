package com.alpsbte.navigator.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    protected ItemMeta itemMeta;

    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder(Material material, int amount, byte color) {
        item = new ItemStack(material, amount, color);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchantment) {
        itemMeta.addEnchant(enchantment,1,true);
        return this;
    }

    public ItemBuilder setItemFlag(ItemFlag itemFlag) {
        itemMeta.addItemFlags(itemFlag);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }
}
