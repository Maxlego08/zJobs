package fr.maxlego08.jobs.zmenu.buttons;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.JobAction;
import fr.maxlego08.jobs.zcore.utils.inventory.Pagination;
import fr.maxlego08.menu.api.button.PaginateButton;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.button.ZButton;
import fr.maxlego08.menu.inventory.inventories.InventoryDefault;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class JobValueButton extends ZButton implements PaginateButton {

    private final ZJobsPlugin plugin;

    public JobValueButton(Plugin plugin) {
        this.plugin = (ZJobsPlugin) plugin;
    }

    @Override
    public void onInventoryOpen(Player player, InventoryDefault inventory, Placeholders placeholders) {
        super.onInventoryOpen(player, inventory, placeholders);
        var targetJob = plugin.getJobManager().getTargetJob(player);
        if (targetJob == null) return;

        placeholders.register("job-name", targetJob.getName());
    }

    @Override
    public boolean hasSpecialRender() {
        return true;
    }

    @Override
    public void onRender(Player player, InventoryDefault inventory) {
        var targetJobs = plugin.getJobManager().getTargetJob(player);
        if (targetJobs == null) return;

        List<JobAction<?>> jobActions = targetJobs.getActions().stream().sorted(Comparator.comparingDouble((ToDoubleFunction<JobAction<?>>) JobAction::getExperience).reversed()).toList();
        Pagination<JobAction<?>> pagination = new Pagination<>();
        jobActions = pagination.paginate(jobActions, this.slots.size(), inventory.getPage());

        for (int i = 0; i != Math.min(jobActions.size(), this.slots.size()); i++) {
            int slot = slots.get(i);
            JobAction<?> jobAction = jobActions.get(i);

            Placeholders placeholders = new Placeholders();
            placeholders.register("experience", format(jobAction.getExperience()));
            placeholders.register("money", format(jobAction.getMoney()));
            placeholders.register("material", jobAction.getDisplayMaterial().name());
            ItemStack itemStack = getItemStack().build(player, false, placeholders);
            jobAction.applyItemStack(itemStack);

            inventory.addItem(slot, itemStack);
        }
    }

    @Override
    public int getPaginationSize(Player player) {
        var targetJobs = plugin.getJobManager().getTargetJob(player);
        return targetJobs == null ? 0 : targetJobs.getActions().size();
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
