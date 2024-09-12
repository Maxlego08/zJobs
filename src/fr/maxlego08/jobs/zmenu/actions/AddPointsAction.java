package fr.maxlego08.jobs.zmenu.actions;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.menu.api.button.Button;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.inventory.inventories.InventoryDefault;
import org.bukkit.entity.Player;

public class AddPointsAction extends Action {

    private final ZJobsPlugin plugin;
    private final int points;

    public AddPointsAction(ZJobsPlugin plugin, int points) {
        this.plugin = plugin;
        this.points = points;
    }

    @Override
    protected void execute(Player player, Button button, InventoryDefault inventoryDefault, Placeholders placeholders) {
        this.plugin.getJobManager().addPoints(player.getUniqueId(), points);
    }
}
