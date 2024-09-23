package fr.maxlego08.jobs.api.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;

public abstract class ActionInfo<T> {

    private final JobActionType actionType;
    private final T value;

    public ActionInfo(JobActionType actionType, T value) {
        this.actionType = actionType;
        this.value = value;
    }

    public JobActionType getActionType() {
        return actionType;
    }

    public T getValue() {
        return value;
    }
}
