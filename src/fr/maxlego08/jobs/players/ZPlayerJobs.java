package fr.maxlego08.jobs.players;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobManager;
import fr.maxlego08.jobs.api.JobReward;
import fr.maxlego08.jobs.api.enums.JobActionType;
import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.api.players.PlayerJobs;
import fr.maxlego08.jobs.api.storage.StorageManager;
import fr.maxlego08.jobs.bossbar.JobBossBar;
import fr.maxlego08.jobs.zcore.utils.ElapsedTime;
import fr.maxlego08.menu.MenuPlugin;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.Placeholders;
import fr.maxlego08.menu.inventory.inventories.InventoryDefault;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ZPlayerJobs implements PlayerJobs {

    private final ZJobsPlugin plugin;
    private final UUID uniqueId;
    private final List<PlayerJob> jobs;
    private JobBossBar jobBossBar;
    private double updateMoney;


    public ZPlayerJobs(ZJobsPlugin plugin, UUID uniqueId, List<PlayerJob> jobs) {
        this.plugin = plugin;
        this.uniqueId = uniqueId;
        this.jobs = jobs;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public List<PlayerJob> getJobs() {
        return jobs;
    }

    @Override
    public boolean hasJob(Job job) {
        return get(job).isPresent();
    }

    @Override
    public void join(Job job) {
        if (!hasJob(job)) {
            PlayerJob playerJob = new ZPlayerJob(job.getFileName(), 0, 0, 0.0);
            jobs.add(playerJob);

            StorageManager storageManager = plugin.getStorageManager();
            storageManager.upsert(this.uniqueId, playerJob, true);
        }
    }

    @Override
    public void leave(Job job) {
        jobs.removeIf(jp -> jp.getJobId().equals(job.getFileName()));

        StorageManager storageManager = plugin.getStorageManager();
        storageManager.deleteJob(this.uniqueId, job.getFileName());
    }

    @Override
    public Optional<PlayerJob> get(Job job) {
        return get(job.getFileName());
    }

    @Override
    public Optional<PlayerJob> get(String jobId) {
        return jobs.stream().filter(jobPlayer -> jobPlayer.getJobId().equals(jobId)).findFirst();
    }

    @Override
    public int size() {
        return jobs.size();
    }

    @Override
    public String toString() {
        return "JobPlayersImpl{" + "uniqueId=" + uniqueId + ", jobs=" + jobs + '}';
    }

    @Override
    public void action(Player player, Object target, JobActionType type) {
        JobManager jobManager = this.plugin.getJobManager();
        for (PlayerJob playerJob : this.jobs) {

            ElapsedTime elapsedTime = new ElapsedTime("Find action for job " + playerJob.getJobId() + " for " + player.getName() + " -> " + type + " : " + target);
            elapsedTime.start();

            var optional = jobManager.getJob(playerJob.getJobId());
            if (optional.isEmpty()) continue;

            var job = optional.get();

            var optionalAction = job.getAction(type, target);
            if (optionalAction.isEmpty()) continue;

            var action = optionalAction.get();

            elapsedTime.endDisplay();

            if (action.getMoney() > 0) {
                this.updateMoney += action.getMoney();
            }

            this.process(player, playerJob, job, action.getExperience(), true);
        }
    }

    @Override
    public void process(Player player, PlayerJob playerJob, Job job, double experience, boolean initialCall) {

        // Mise à jour des niveaux
        playerJob.process(experience);
        this.updateBossBar(player, playerJob, job);

        double maxExperience = job.getExperience(playerJob.getLevel(), playerJob.getPrestige());

        // On peut augmenter le niveau du jouer
        if (playerJob.getExperience() >= maxExperience) {
            double remainingExperience = playerJob.getExperience() - maxExperience;

            // Mise à jour du niveau
            playerJob.nextLevel();
            playerJob.setExperience(0);

            // On vérifie si on peut changer de prestige
            if (playerJob.getLevel() > job.getMaxLevels()) {
                playerJob.setLevel(1);
                playerJob.nextPrestige();

                // On va vérifier le prestige
                // ToDo
            }

            processReward(player, job, playerJob.getLevel(), playerJob.getLevel() - 1, playerJob.getPrestige(), playerJob.getPrestige() - 1);

            if (this.jobBossBar != null) {
                this.jobBossBar.updateMaxExperience(job.getExperience(playerJob.getLevel(), playerJob.getPrestige()));
            }
            this.updateBossBar(player, playerJob, job);

            if (remainingExperience > 0) {
                this.process(player, playerJob, job, remainingExperience, false);
            }
        }

        if (initialCall) {
            StorageManager storageManager = plugin.getStorageManager();
            storageManager.upsert(uniqueId, playerJob, false);
        }
    }


    private void processReward(Player player, Job job, int newLevel, int oldLevel, int newPrestige, int oldPrestige) {
        if (player == null) return;

        Placeholders placeholders = new Placeholders();
        placeholders.register("level", String.valueOf(newLevel));
        placeholders.register("previous-level", String.valueOf(oldLevel));
        placeholders.register("prestige", String.valueOf(newPrestige));
        placeholders.register("previous-prestige", String.valueOf(oldPrestige));

        InventoryDefault inventoryDefault = new InventoryDefault();
        inventoryDefault.setPlugin(MenuPlugin.getInstance());

        this.plugin.getScheduler().runTask(player.getLocation(), () -> {
            for (JobReward reward : job.getRewards()) {
                int rewardLevel = reward.getLevel();
                int rewardPrestige = reward.getPrestige();
                if ((rewardLevel == -1 || rewardLevel == newLevel) && (rewardPrestige == -1 || rewardPrestige == newPrestige)) {
                    for (Action rewardAction : reward.getActions()) {
                        rewardAction.preExecute(player, null, inventoryDefault, placeholders);
                    }
                }
            }
        });
    }

    private void updateBossBar(Player player, PlayerJob playerJob, Job job) {
        if (player == null) return;

        if (this.jobBossBar == null || this.jobBossBar.isExpired()) {

            this.jobBossBar = new JobBossBar(plugin, player, playerJob.getExperience(), job.getExperience(playerJob.getLevel(), playerJob.getPrestige()), playerJob.getLevel(), playerJob.getPrestige(), job.getName());
            this.jobBossBar.resetTimer();
        } else {

            this.jobBossBar.resetTimer();
            this.plugin.getScheduler().runTaskAsynchronously(() -> this.jobBossBar.updateExperience(playerJob.getExperience(), playerJob.getLevel(), playerJob.getPrestige()));
        }
    }

    @Override
    public void updateJobEconomies() {
        if (this.updateMoney <= 0) return;

        var offlinePlayer = Bukkit.getOfflinePlayer(this.uniqueId);
        this.plugin.getEconomyProvider().depositMoney(offlinePlayer, this.updateMoney);

        this.updateMoney = 0;
    }
}
