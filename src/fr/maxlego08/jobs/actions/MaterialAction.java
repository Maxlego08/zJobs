package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;

public class MaterialAction extends ZJobAction<Material> {

    private final JobActionType type;
    public MaterialAction(Material target, double experience, double money, JobActionType type, Material displayMaterial) {
        super(target, experience, money, displayMaterial);
        this.type = type;
    }

    @Override
    public JobActionType getType() {
        return this.type;
    }

    @Override
    public boolean isAction(Object target) {
        return target instanceof Material material && material == this.target;
    }
}
