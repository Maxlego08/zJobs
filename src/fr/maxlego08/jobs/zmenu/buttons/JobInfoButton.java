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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        Placeholders placeholders = new Placeholders();

        PlayerJobs playerJobs = jobManager.getPlayerJobs(player.getUniqueId()).orElse(new ZPlayerJobs(plugin, player.getUniqueId(), List.of(), 0));
        PlayerJob playerJob = playerJobs.get(job).orElse(new ZPlayerJob(job.getFileName(), 0, 0, 0.0));

        double maxExperience = job.getExperience(playerJob.getLevel(), playerJob.getPrestige());
        placeholders.register("experience", format(playerJob.getExperience()));
        placeholders.register("max-experience", format(maxExperience));

        placeholders.register("level", format(playerJob.getLevel()));
        placeholders.register("max-level", format(job.getMaxLevels()));

        placeholders.register("prestige", format(playerJob.getPrestige()));
        placeholders.register("max-prestige", format(job.getMaxPrestiges()));

        placeholders.register("experience-progressbar", Config.progressBarExperience.getProgressBar(playerJob.getExperience(), maxExperience));
        placeholders.register("level-progressbar", Config.progressBarLevel.getProgressBar(playerJob.getLevel(), job.getMaxLevels()));
        placeholders.register("prestige-progressbar", Config.progressBarPrestige.getProgressBar(playerJob.getPrestige(), job.getMaxPrestiges()));

        return getItemStack().build(player, false, placeholders);
    }
}
