package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;
import org.bukkit.Tag;

public class TagAction extends ZJobAction<Tag<Material>> {

    private final JobActionType type;
    public TagAction(Tag<Material> target, double experience, double money, JobActionType type, Material displayMaterial) {
        super(target, experience, money, displayMaterial);
        this.type = type;
    }

    @Override
    public JobActionType getType() {
        return this.type;
    }

    @Override
    public boolean isAction(Object target) {
        return target instanceof Material material && this.target.isTagged(material);
    }
}
