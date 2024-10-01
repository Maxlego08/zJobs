package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnchantmentAction extends ZJobAction<Material> {

    private final Enchantment enchantment;
    private final int minimumLevel;
    private final int minimumCost;

    public EnchantmentAction(Material target, double experience, double money, Enchantment enchantment, int minimumLevel, int minimumCost, Material displayMaterial) {
        super(target, experience, money, displayMaterial);
        this.enchantment = enchantment;
        this.minimumLevel = minimumLevel;
        this.minimumCost = minimumCost;
    }

    @Override
    public JobActionType getType() {
        return JobActionType.ENCHANT;
    }

    @Override
    public boolean isAction(Object target) {

        if (target instanceof EnchantItemEvent event) {

            ItemStack itemStack = event.getItem();
            var enchants = event.getEnchantsToAdd();

            if (this.target != null && itemStack.getType() != this.target) return false;

            for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                var enchantment = entry.getKey();
                int value = entry.getValue();

                if (enchantment.equals(this.enchantment) && value >= minimumLevel) {
                    return true;
                }
            }

            return minimumCost >= event.getExpLevelCost();
        }

        return false;
    }
}
