package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.entity.EntityType;

public class EntityAction extends ZJobAction<EntityType> {

    private final JobActionType actionType;
    public EntityAction(EntityType target, double experience, double money, JobActionType actionType) {
        super(target, experience, money);
        this.actionType = actionType;
    }

    @Override
    public JobActionType getType() {
        return actionType;
    }

    @Override
    public boolean isAction(Object target) {
        return target instanceof EntityType entityType && this.target == entityType;
    }
}
