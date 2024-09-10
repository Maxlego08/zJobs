package fr.maxlego08.jobs.players;

import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.dto.PlayerJobDTO;

public class ZPlayerJob implements PlayerJob {

    private final String jobId;
    private int level;
    private int prestige;
    private double experience;

    // Constructeur
    public ZPlayerJob(String jobId, int level, int prestige, double experience) {
        this.jobId = jobId;
        this.level = level;
        this.prestige = prestige;
        this.experience = experience;
    }

    public ZPlayerJob(PlayerJobDTO playerJobDTO) {
        this(playerJobDTO.job_id(), playerJobDTO.level(), playerJobDTO.prestige(), playerJobDTO.experience());
    }

    @Override
    public String getJobId() {
        return jobId;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void process(double experience) {
        this.experience += experience;
    }

    @Override
    public void nextLevel() {
        this.level += 1;
    }

    @Override
    public void nextPrestige() {
        this.prestige += 1;
    }

    @Override
    public void addLevel(int level) {
        this.level += level;
    }

    @Override
    public void removeLevel(int level) {
        this.level -= level;
    }

    @Override
    public int getPrestige() {
        return prestige;
    }

    @Override
    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }

    @Override
    public double getExperience() {
        return experience;
    }

    @Override
    public void setExperience(double experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "JobPlayerImpl{" +
                "jobId='" + jobId + '\'' +
                ", level=" + level +
                ", prestige=" + prestige +
                ", experience=" + experience +
                '}';
    }
}
