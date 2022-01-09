package com.alpsbte.navigator.core.hotbar.items;

import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

public class DefaultEvent extends Event {

    @Override
    public Material getMaterial() {
        return Material.FIREWORK;
    }

    @Override
    public String getTitle() {
        return "§b§lEVENT SERVER §7(Right Click)";
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList("There is currently no event...");
    }

    @Override
    public List<String> getFeatures() {
        return null;
    }
}
