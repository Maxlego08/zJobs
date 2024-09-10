package fr.maxlego08.jobs.api.players;

public interface PlayerJob {

    String getJobId();

    int getLevel();

    int getPrestige();

    double getExperience();

    void setExperience(double experience);

    void setLevel(int level);

    void setPrestige(int prestige);

    void process(double experience);

    void nextLevel();

    void nextPrestige();

    void addLevel(int level);

    void removeLevel(int level);

    void removeExperience(double experience);

    void addPrestige(int prestige);

    void removePrestige(int prestige);

}
