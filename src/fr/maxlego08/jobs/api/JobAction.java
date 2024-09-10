package fr.maxlego08.jobs.api;

import fr.maxlego08.jobs.api.enums.JobActionType;

public interface JobAction<T> {

    JobActionType getType();

    T getTarget();

    double getExperience();

    double getMoney();

    boolean isAction(Object target);
}
