package fr.maxlego08.jobs.zmenu.actions;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.inventory.inventories.InventoryDefault;
import org.bukkit.entity.Player;

public class ClaimRewardAction extends Action {

    private final ZJobsPlugin plugin;
    private final String reward;

    public ClaimRewardAction(ZJobsPlugin plugin, String reward) {
        this.plugin = plugin;
        this.reward = reward;
    }

    @Override
    protected void execute(Player player, Button button, InventoryDefault inventoryDefault, Placeholders placeholders) {
        var optional = plugin.getJobManager().getPlayerJobs(player.getUniqueId());
        if (optional.isEmpty()) return;

        var playerJobs = optional.get();
        playerJobs.getRewards().add(reward);
        plugin.getStorageManager().upsert(player.getUniqueId(), playerJobs.getRewards());
    }
}
