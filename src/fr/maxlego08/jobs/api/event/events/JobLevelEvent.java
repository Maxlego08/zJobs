package fr.maxlego08.jobs.api.event.events;

import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.event.CancelledJobEvent;
import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.api.players.PlayerJobs;
import org.bukkit.entity.Player;

public class JobLevelEvent extends CancelledJobEvent {

    private final Player player;
    private final PlayerJobs playerJobs;
    private final PlayerJob playerJob;
    private final Job job;
    private int level;
    private double experience;

    public JobLevelEvent(Player player, PlayerJobs playerJobs, PlayerJob playerJob, Job job, int level, double experience) {
        this.player = player;
        this.playerJobs = playerJobs;
        this.playerJob = playerJob;
        this.job = job;
        this.level = level;
        this.experience = experience;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }
}
