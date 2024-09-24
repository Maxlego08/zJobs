package fr.maxlego08.jobs.api.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantAction extends ActionInfo<EnchantItemEvent> {

    public EnchantAction(JobActionType actionType, EnchantItemEvent value) {
        super(actionType, value);
    }
}
