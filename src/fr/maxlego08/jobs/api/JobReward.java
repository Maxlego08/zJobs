package fr.maxlego08.jobs.api;

import fr.maxlego08.menu.api.requirement.Action;

import java.util.List;

public interface JobReward {

    int getLevel();

    int getPrestige();

    List<Action> getActions();

}
