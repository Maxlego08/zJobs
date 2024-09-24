package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;

public class BlockAction extends ZJobAction<Material> {

    private final JobActionType type;
    public BlockAction(Material target, double experience, double money, JobActionType type) {
        super(target, experience, money);
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
