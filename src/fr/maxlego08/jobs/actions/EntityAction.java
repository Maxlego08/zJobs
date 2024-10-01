package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class EntityAction extends ZJobAction<EntityType> {

    private final JobActionType actionType;
    public EntityAction(EntityType target, double experience, double money, JobActionType actionType, Material displayMaterial) {
        super(target, experience, money, displayMaterial);
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
