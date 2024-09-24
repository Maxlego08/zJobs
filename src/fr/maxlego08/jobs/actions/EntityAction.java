package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.entity.EntityType;

public class EntityAction extends ZJobAction<EntityType> {

    public EntityAction(EntityType target, double experience, double money) {
        super(target, experience, money);
    }

    @Override
    public JobActionType getType() {
        return JobActionType.TAME;
    }

    @Override
    public boolean isAction(Object target) {
        return target instanceof EntityType entityType && this.target == entityType;
    }
}
