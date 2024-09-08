package fr.maxlego08.jobs;

import fr.maxlego08.jobs.api.JobReward;
import fr.maxlego08.menu.api.requirement.Action;

import java.util.List;

public class ZJobReward implements JobReward {

    private final int level;
    private final int prestige;
    private final List<Action> actions;

    public ZJobReward(int level, int prestige, List<Action> actions) {
        this.level = level;
        this.prestige = prestige;
        this.actions = actions;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getPrestige() {
        return prestige;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }
}
