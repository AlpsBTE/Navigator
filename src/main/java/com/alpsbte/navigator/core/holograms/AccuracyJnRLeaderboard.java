package com.alpsbte.navigator.core.holograms;

import com.alpsbte.alpslib.hologram.HolographicPagedDisplay;
import com.alpsbte.navigator.NavigatorPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AccuracyJnRLeaderboard extends HolographicPagedDisplay {
    protected AccuracyJnRLeaderboard(String id) {
        super(id, NavigatorPlugin.getPlugin());
    }

    @Override
    public String getTitle() {
        return "§b§lACCURACY PARKOUR LEADERBOARD";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.FEATHER);
    }

    @Override
    public List<DataLine<?>> getContent() {
        return HologramManager.getLeaderboardContent("JumpAndRunDifficult");
    }

    @Override
    public List<DataLine<?>> getFooter() {
        return Collections.singletonList(new TextLine(contentSeparator));
    }

    @Override
    public long getInterval() {
        return 20*30;
    }

    @Override
    public List<String> getPages() {
        return Collections.singletonList("accuracy-jnr-leaderboard");
    }
}
