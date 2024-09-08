package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.JobActionType;
import org.bukkit.Material;
import org.bukkit.Tag;

public class BlockTagAction extends ZJobAction<Tag<Material>> {

    public BlockTagAction(Tag<Material> target, double experience, double money) {
        super(target, experience, money);
    }

    @Override
    public JobActionType getType() {
        return JobActionType.BLOCK_BREAK;
    }

    @Override
    public boolean isAction(Object target) {
        return target instanceof Material material && this.target.isTagged(material);
    }
}
