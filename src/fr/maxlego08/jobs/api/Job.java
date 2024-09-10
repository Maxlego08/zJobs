package fr.maxlego08.jobs.api;

import fr.maxlego08.jobs.api.enums.JobActionType;

import java.util.Collection;
import java.util.Optional;

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

    Optional<JobAction<?>> getAction(JobActionType action, Object target);

    boolean canLeave();

    boolean canJoin();
}
