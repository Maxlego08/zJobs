package fr.maxlego08.jobs.api.enums;

import fr.maxlego08.jobs.api.actions.ActionInfo;
import fr.maxlego08.jobs.api.actions.CommandAction;
import fr.maxlego08.jobs.api.actions.EntityAction;
import fr.maxlego08.jobs.api.actions.MaterialAction;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

public enum JobActionType {

    BLOCK_BREAK,
    BLOCK_PLACE,
    KILL_ENTITY,
    FARMING,
    FISHING,
    COMMAND,

    ;

    public ActionInfo<?> toAction(Object target) {
        return switch (this) {
            case BLOCK_BREAK, BLOCK_PLACE, FARMING, FISHING -> new MaterialAction(this, (Material) target);
            case KILL_ENTITY -> new EntityAction(this, (Entity) target);
            case COMMAND -> new CommandAction(target == null ? "" : (String) target);
        };
    }

    public boolean isMaterial() {
        return switch (this) {
            case BLOCK_BREAK, BLOCK_PLACE, FARMING, FISHING -> true;
            case COMMAND, KILL_ENTITY -> false;
        };
    }
}
