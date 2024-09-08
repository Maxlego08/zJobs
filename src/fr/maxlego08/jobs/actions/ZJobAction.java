package fr.maxlego08.jobs.actions;

import fr.maxlego08.jobs.api.JobAction;

public abstract class ZJobAction<T> implements JobAction<T> {

    protected final T target;
    private final double experience;
    private final double money;

    public ZJobAction(T target, double experience, double money) {
        this.target = target;
        this.experience = experience;
        this.money = money;
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
}
