package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.JobAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class ZJobAction<T> implements JobAction<T> {

    protected final T target;
    private final double experience;
    private final double money;
    private final Material displayMaterial;

    public ZJobAction(T target, double experience, double money, Material displayMaterial) {
        this.target = target;
        this.experience = experience;
        this.money = money;
        this.displayMaterial = displayMaterial;
    }

    @Override
    public T getTarget() {
        return this.target;
    }

    @Override
    public double getExperience() {
        return this.experience;
    }

    @Override
    public double getMoney() {
        return this.money;
    }

    @Override
    public String toString() {
        return "ZJobAction{" +
                "target=" + target +
                ", experience=" + experience +
                ", money=" + money +
                '}';
    }

    @Override
    public void applyItemStack(ItemStack itemStack) {

    }

    @Override
    public Material getDisplayMaterial() {
        return this.displayMaterial == null ? Material.STONE : this.displayMaterial;
    }
}
