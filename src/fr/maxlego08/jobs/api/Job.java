package fr.maxlego08.jobs.api;

import java.util.Collection;

public interface Job {

    String getName();

    String getFileName();

    double getBaseExperience();

    int getMaxLevels();

    int getMaxPrestiges();

    String getFormula();

    Collection<JobAction<?>> getActions();

    Collection<JobReward> getRewards();

    double[][] getMatrix();

    double getExperienceForNextLevel(int level, int prestige);

    double getExperience(int level, int prestige);

}
