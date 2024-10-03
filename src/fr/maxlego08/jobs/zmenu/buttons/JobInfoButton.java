package fr.maxlego08.jobs.zmenu.buttons;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobManager;
import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.api.players.PlayerJobs;
import fr.maxlego08.jobs.players.ZPlayerJob;
import fr.maxlego08.jobs.players.ZPlayerJobs;
import fr.maxlego08.jobs.save.Config;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.button.ZButton;
import fr.maxlego08.menu.inventory.inventories.InventoryDefault;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;

public class JobInfoButton extends ZButton {

    private final ZJobsPlugin plugin;
    private final Job job;
    private final JobManager jobManager;

    public JobInfoButton(ZJobsPlugin plugin, Job job) {
        this.plugin = plugin;
        this.job = job;
        this.jobManager = plugin.getJobManager();
    }

    @Override
    public ItemStack getCustomItemStack(Player player) {

        if (this.job == null) {
            this.plugin.getLogger().severe("Impossible to find the job !");
            return super.getCustomItemStack(player);
        }

        Placeholders placeholders = new Placeholders();

        PlayerJobs playerJobs = this.jobManager.getPlayerJobs(player.getUniqueId()).orElse(new ZPlayerJobs(this.plugin, player.getUniqueId(), List.of(), 0, new HashSet<>()));
        PlayerJob playerJob = playerJobs.get(this.job).orElse(new ZPlayerJob(this.job.getFileName(), 0, 0, 0.0));

        double maxExperience = this.job.getExperience(playerJob.getLevel(), playerJob.getPrestige());
        placeholders.register("experience", format(playerJob.getExperience()));
        placeholders.register("max-experience", format(maxExperience));

        placeholders.register("level", format(playerJob.getLevel()));
        placeholders.register("max-level", format(this.job.getMaxLevels()));

        placeholders.register("prestige", format(playerJob.getPrestige()));
        placeholders.register("max-prestige", format(this.job.getMaxPrestiges()));

        placeholders.register("experience-progressbar", Config.progressBarExperience.getProgressBar(playerJob.getExperience(), maxExperience));
        placeholders.register("level-progressbar", Config.progressBarLevel.getProgressBar(playerJob.getLevel(), this.job.getMaxLevels()));
        placeholders.register("prestige-progressbar", Config.progressBarPrestige.getProgressBar(playerJob.getPrestige(), this.job.getMaxPrestiges()));

        return getItemStack().build(player, false, placeholders);
    }

    @Override
    public void onClick(Player player, InventoryClickEvent event, InventoryDefault inventory, int slot, Placeholders placeholders) {
        super.onClick(player, event, inventory, slot, placeholders);
        this.plugin.getJobManager().setTargetJob(player, this.job);
        var inventoryManager = this.plugin.getInventoryManager();
        inventoryManager.getInventory(this.plugin, "job_info").ifPresent(newInventory -> inventoryManager.openInventoryWithOldInventories(player, newInventory, 1));
    }
}
