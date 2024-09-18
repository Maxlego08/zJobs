package fr.maxlego08.jobs.hooks;

import fr.maxlego08.jobs.api.hooks.BlockHook;
import org.bukkit.block.Block;

public class EmptyHook implements BlockHook {

    @Override
    public boolean isTracked(Block block) {
        return false;
    }
}
