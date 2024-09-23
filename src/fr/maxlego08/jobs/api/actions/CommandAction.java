package fr.maxlego08.jobs.api.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;

public class CommandAction extends ActionInfo<String> {
    public CommandAction(String value) {
        super(JobActionType.COMMAND, value);
    }
}
