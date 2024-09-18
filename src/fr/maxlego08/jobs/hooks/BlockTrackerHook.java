package fr.maxlego08.jobs.hooks;

import dev.krakenied.blocktracker.bukkit.BukkitBlockTrackerPlugin;
import fr.maxlego08.jobs.api.hooks.BlockHook;
import org.bukkit.block.Block;

public class BlockTrackerHook implements BlockHook {
    @Override
    public boolean isTracked(Block block) {
        return BukkitBlockTrackerPlugin.isTracked(block);
    }
}
