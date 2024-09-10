package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;

public class BlockAction extends ZJobAction<Material> {

    public BlockAction(Material target, double experience, double money) {
        super(target, experience, money);
    }

    @Override
    public JobActionType getType() {
        return JobActionType.BLOCK_BREAK;
    }

    @Override
    public boolean isAction(Object target) {
        return target instanceof Material material && material == this.target;
    }
}
