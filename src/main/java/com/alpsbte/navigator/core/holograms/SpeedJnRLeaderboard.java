package com.alpsbte.navigator.core.holograms;

import com.alpsbte.alpslib.hologram.HolographicPagedDisplay;
import com.alpsbte.navigator.NavigatorPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SpeedJnRLeaderboard extends HolographicPagedDisplay {
    protected SpeedJnRLeaderboard(String id) {
        super(id, NavigatorPlugin.getPlugin());
    }

    @Override
    public String getTitle() {
        return "§b§lSPEED PARKOUR LEADERBOARD";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.FEATHER);
    }

    @Override
    public List<DataLine<?>> getContent() {
        return HologramManager.getLeaderboardContent("SpeedJumpAndRun");
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
        return Collections.singletonList("speed-jnr-leaderboard");
    }
}
