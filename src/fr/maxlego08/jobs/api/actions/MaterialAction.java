package fr.maxlego08.jobs.api.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;

public class MaterialAction extends ActionInfo<Material>  {
    public MaterialAction(JobActionType actionType, Material value) {
        super(actionType, value);
    }
}
