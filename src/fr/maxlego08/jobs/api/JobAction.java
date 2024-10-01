package fr.maxlego08.jobs.api;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface JobAction<T> {

    JobActionType getType();

    T getTarget();

    double getExperience();

    double getMoney();

    boolean isAction(Object target);

    void applyItemStack(ItemStack itemStack);

    Material getDisplayMaterial();
}
