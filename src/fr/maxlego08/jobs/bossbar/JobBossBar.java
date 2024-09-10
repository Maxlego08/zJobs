package fr.maxlego08.jobs.bossbar;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.component.PaperComponent;
import fr.maxlego08.jobs.save.Config;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.utils.ZUtils;
import fr.maxlego08.menu.api.scheduler.ZScheduler;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;

public class JobBossBar extends ZUtils {

    private final Player player;
    private final BossBar bossBar;
    private final ZJobsPlugin plugin;
    private final String jobName;
    private double maxExperience;
    private double currentExperience;
    private ZScheduler task;

    public JobBossBar(ZJobsPlugin plugin, Player player, double currentExperience, double maxExperience, int level, int prestige, String jobName) {

        PaperComponent paperComponent = plugin.getPaperComponent();

        this.plugin = plugin;
        this.player = player;
        this.jobName = jobName;
        this.currentExperience = currentExperience;
        this.maxExperience = maxExperience;

        float progress = (float) ((float) currentExperience / maxExperience);
        System.out.println(progress + " - " + currentExperience + " / " + maxExperience);
        this.bossBar = paperComponent.createBossBar(getMessage(
                Message.PROGRESSION_BOSSBAR,
                "%job-experience%", Config.decimalFormat.format(currentExperience),
                "%job-max-experience%", Config.decimalFormat.format(maxExperience),
                "%job-name%", jobName,
                "%job-prestige%", prestige,
                "%job-level%", level
        ), Config.progressionBarColor, Config.progressionBarOverlay, Math.max(0, Math.min(1, progress)));
        this.bossBar.addViewer(player);
    }

    private void updateBossBar() {
        float progress = (float) ((float) this.currentExperience / this.maxExperience);
        this.bossBar.progress(Math.max(0, Math.min(1, progress)));
    }

    public void updateExperience(double newExperience, int level, int prestige) {
        this.currentExperience = newExperience;
        updateBossBar();
        this.bossBar.name(this.plugin.getPaperComponent().getComponent(getMessage(
                Message.PROGRESSION_BOSSBAR,
                "%job-experience%", Config.decimalFormat.format(currentExperience),
                "%job-max-experience%", Config.decimalFormat.format(maxExperience),
                "%job-name%", jobName,
                "%job-prestige%", prestige,
                "%job-level%", level
        )));
    }

    public void updateMaxExperience(double maxExperience) {
        this.maxExperience = maxExperience;
    }

    public void resetTimer() {
        if (task != null) task.cancel();

        this.task = this.plugin.getScheduler().runTaskLater(this.player.getLocation(), 100L, () -> {
            this.bossBar.removeViewer(player);
            this.task = null;
        });
    }

    public boolean isExpired() {
        return this.task == null;
    }
}