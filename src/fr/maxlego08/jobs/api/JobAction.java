package fr.maxlego08.jobs.api;

public interface JobAction<T> {

    JobActionType getType();

    T getTarget();

    double getExperience();

    double getMoney();
}
