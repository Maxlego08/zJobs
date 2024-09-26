package fr.maxlego08.jobs.api.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.event.inventory.BrewEvent;

public class BrewAction extends ActionInfo<BrewEvent> {
    public BrewAction(JobActionType actionType, BrewEvent value) {
        super(actionType, value);
    }
}
