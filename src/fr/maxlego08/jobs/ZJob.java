package fr.maxlego08.jobs;

import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobAction;
import fr.maxlego08.jobs.api.JobActionType;
import fr.maxlego08.jobs.api.JobReward;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ZJob implements Job {

    private final String name;
    private final String fileName;
    private final double baseExperience;
    private final int maxLevels;
    private final int maxPrestiges;
    private final String formula;
    private final List<JobAction<?>> jobActions;
    private final List<JobReward> jobRewards;
    private final double[][] matrix;

    public ZJob(String name, String fileName, double baseExperience, int maxLevels, int maxPrestiges, String formula, List<JobAction<?>> jobActions, List<JobReward> jobRewards) {
        this.name = name;
        this.fileName = fileName;
        this.baseExperience = baseExperience;
        this.maxLevels = maxLevels;
        this.maxPrestiges = maxPrestiges;
        this.formula = formula;
        this.jobActions = jobActions;
        this.jobRewards = jobRewards;
        this.matrix = new double[this.maxLevels][this.maxPrestiges + 1];

        for (int prestige = 0; prestige <= this.maxPrestiges; prestige++) {
            for (int level = 1; level <= this.maxLevels; level++) {
                this.matrix[level - 1][prestige] = getExperienceForNextLevel(level, prestige);
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public double getBaseExperience() {
        return this.baseExperience;
    }

    @Override
    public int getMaxLevels() {
        return this.maxLevels;
    }

    @Override
    public int getMaxPrestiges() {
        return this.maxPrestiges;
    }

    @Override
    public String getFormula() {
        return this.formula;
    }

    @Override
    public Collection<JobAction<?>> getActions() {
        return Collections.unmodifiableCollection(this.jobActions);
    }

    @Override
    public Collection<JobReward> getRewards() {
        return Collections.unmodifiableCollection(this.jobRewards);
    }

    @Override
    public double[][] getMatrix() {
        return this.matrix;
    }

    @Override
    public double getExperienceForNextLevel(int level, int prestige) {
        Expression expression = new ExpressionBuilder(formula)
                .variable("baseExperience")
                .variable("level")
                .variable("prestige")
                .variable("maxPrestiges")
                .build()
                .setVariable("baseExperience", baseExperience)
                .setVariable("level", level)
                .setVariable("prestige", prestige)
                .setVariable("maxPrestiges", maxPrestiges);

        return expression.evaluate();
    }

    @Override
    public double getExperience(int level, int prestige) {
        try {
            int adjustedLevel = level - 1;

            if (adjustedLevel >= 0 && adjustedLevel < this.matrix.length && prestige >= 0 && prestige < this.matrix[adjustedLevel].length) {
                return this.matrix[adjustedLevel][prestige];
            } else {
                return 1;
            }
        } catch (Exception ignored) {
            return 1;
        }
    }

    @Override
    public Optional<JobAction<?>> getAction(JobActionType action, Object target) {
        return this.jobActions.stream().filter(jobAction -> jobAction.getType() == action && jobAction.isAction(target)).findFirst();
    }
}
