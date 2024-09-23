package fr.maxlego08.jobs.api.event.events;

import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobReward;
import fr.maxlego08.jobs.api.event.CancelledJobEvent;
import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.api.players.PlayerJobs;
import org.bukkit.entity.Player;

public class JobRewardEvent extends CancelledJobEvent {

    private final Player player;
    private final PlayerJobs playerJobs;
    private final PlayerJob playerJob;
    private final Job job;
    private final int oldLevel;
    private final int oldPrestige;
    private final int newLevel;
    private final int newPrestige;
    private JobReward jobReward;

    public JobRewardEvent(Player player, PlayerJobs playerJobs, PlayerJob playerJob, Job job, int oldLevel, int oldPrestige, JobReward jobReward, int newLevel, int newPrestige) {
        this.player = player;
        this.playerJobs = playerJobs;
        this.playerJob = playerJob;
        this.job = job;
        this.oldLevel = oldLevel;
        this.oldPrestige = oldPrestige;
        this.jobReward = jobReward;
        this.newLevel = newLevel;
        this.newPrestige = newPrestige;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerJobs getPlayerJobs() {
        return playerJobs;
    }

    public PlayerJob getPlayerJob() {
        return playerJob;
    }

    public Job getJob() {
        return job;
    }

    public int getOldLevel() {
        return oldLevel;
    }

    public int getOldPrestige() {
        return oldPrestige;
    }

    public JobReward getJobReward() {
        return jobReward;
    }

    public void setJobReward(JobReward jobReward) {
        this.jobReward = jobReward;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public int getNewPrestige() {
        return newPrestige;
    }
}
