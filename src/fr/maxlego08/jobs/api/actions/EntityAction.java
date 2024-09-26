package fr.maxlego08.jobs.api.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityAction extends ActionInfo<EntityType> {
    public EntityAction(JobActionType actionType, EntityType value) {
        super(actionType, value);
    }
}
