package fr.maxlego08.jobs.api.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.entity.Entity;

public class EntityAction extends ActionInfo<Entity> {
    public EntityAction(JobActionType actionType, Entity value) {
        super(actionType, value);
    }
}
