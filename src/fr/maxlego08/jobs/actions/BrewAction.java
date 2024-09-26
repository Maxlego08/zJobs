package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Material;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class BrewAction extends ZJobAction<PotionType> {

    private final Material potionMaterial;
    private final Material ingredient;

    public BrewAction(PotionType target, double experience, double money, Material potionMaterial, Material ingredient) {
        super(target, experience, money);
        this.potionMaterial = potionMaterial;
        this.ingredient = ingredient;
    }

    @Override
    public JobActionType getType() {
        return JobActionType.BREW;
    }

    @Override
    public boolean isAction(Object target) {

        if (target instanceof BrewEvent event) {

            var result = event.getResults();
            var contents = event.getContents();
            var ingredient = contents.getIngredient();

            if (result.size() >= 1) {
                ItemStack itemStack = result.get(0);

                if (this.potionMaterial == itemStack.getType()) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (itemMeta instanceof PotionMeta potionMeta) {

                        if (potionMeta.getBasePotionType() == this.target) {
                            return true;
                        }
                    }
                }
            }

            return ingredient != null && ingredient.getType() == this.ingredient;
        }

        return false;
    }
}
