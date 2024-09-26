package fr.maxlego08.jobs.api.enums;

import fr.maxlego08.jobs.api.actions.ActionInfo;
import fr.maxlego08.jobs.api.actions.BrewAction;
import fr.maxlego08.jobs.api.actions.CommandAction;
import fr.maxlego08.jobs.api.actions.EnchantAction;
import fr.maxlego08.jobs.api.actions.EntityAction;
import fr.maxlego08.jobs.api.actions.MaterialAction;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.BrewEvent;

public enum JobActionType {

    BLOCK_BREAK,
    BLOCK_PLACE,
    KILL_ENTITY,
    FARMING,
    FISHING,
    COMMAND,
    TAME,
    ENCHANT,
    BREW;

    public ActionInfo<?> toAction(Object target) {
        return switch (this) {
            case BLOCK_BREAK, BLOCK_PLACE, FARMING, FISHING -> new MaterialAction(this, (Material) target);
            case KILL_ENTITY, TAME -> new EntityAction(this, (Entity) target);
            case COMMAND -> new CommandAction(target == null ? "" : (String) target);
            case ENCHANT -> new EnchantAction(this, (EnchantItemEvent) target);
            case BREW -> new BrewAction(this, (BrewEvent) target);
        };
    }

    public boolean isMaterial() {
        return switch (this) {
            case BLOCK_BREAK, BLOCK_PLACE, FARMING, FISHING -> true;
            case COMMAND, KILL_ENTITY, TAME, ENCHANT, BREW -> false;
        };
    }

    public boolean isEntityType() {
        return this == KILL_ENTITY || this == TAME;
    }
}
