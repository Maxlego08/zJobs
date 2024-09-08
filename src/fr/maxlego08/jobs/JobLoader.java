package fr.maxlego08.jobs;

import fr.maxlego08.jobs.actions.BlockAction;
import fr.maxlego08.jobs.actions.BlockTagAction;
import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobAction;
import fr.maxlego08.jobs.api.JobActionType;
import fr.maxlego08.jobs.api.JobReward;
import fr.maxlego08.jobs.zcore.utils.TagRegistry;
import fr.maxlego08.jobs.zcore.utils.loader.Loader;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobLoader implements Loader<Job> {

    private final ZJobsPlugin plugin;
    private final File file;

    public JobLoader(ZJobsPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
    }

    @Override
    public Job load(YamlConfiguration configuration, String path) {

        double baseExperience = configuration.getDouble("base-experience", 100);
        int maxLevels = configuration.getInt("max-levels", 100);
        int maxPrestiges = configuration.getInt("max-prestiges", 100);
        String name = configuration.getString("name");
        String formula = configuration.getString("formula", "baseExperience * (1 + 0.05 * level + 0.005 * level^2) * (1 + 0.3 * (prestige / maxPrestiges))");
        List<JobAction<?>> jobActions = loadActions(configuration);

        List<JobReward> jobRewards = new ArrayList<>();
        configuration.getMapList("rewards").forEach(map -> {
            TypedMapAccessor accessor = new TypedMapAccessor((Map<String, Object>) map);
            int level = accessor.getInt("level");
            int prestige = accessor.getInt("prestige");
            List<Action> actions = plugin.getButtonManager().loadActions(map.containsKey("actions") ? (List<Map<String, Object>>) map.get("actions") : new ArrayList<>(), path, file);
            jobRewards.add(new ZJobReward(level, prestige, actions));
        });

        return new ZJob(name, file.getName().replace(".yml", ""), baseExperience, maxLevels, maxPrestiges, formula, jobActions, jobRewards);
    }

    private List<JobAction<?>> loadActions(YamlConfiguration configuration) {
        List<JobAction<?>> jobActions = new ArrayList<>();

        configuration.getMapList("actions").forEach(map -> {
            TypedMapAccessor accessor = new TypedMapAccessor((Map<String, Object>) map);
            double experience = accessor.getDouble("experience", 0);
            double money = accessor.getDouble("money", 0);
            try {

                JobActionType jobActionType = JobActionType.valueOf(accessor.getString("type").toUpperCase());
                if (jobActionType == JobActionType.BLOCK_BREAK) {
                    if (accessor.contains("material")) {
                        Material material = Material.valueOf(accessor.getString("material").toUpperCase());
                        jobActions.add(new BlockAction(material, experience, money));
                    } else if (accessor.contains("tag")) {
                        Tag<Material> tag = TagRegistry.getTag(accessor.getString("tag").toUpperCase());
                        jobActions.add(new BlockTagAction(tag, experience, money));
                    } else {
                        plugin.getLogger().severe("Impossible to find the tag or material for BLOCK BREAK in file " + file.getAbsolutePath());
                    }
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return jobActions;
    }

    @Override
    public void save(Job object, YamlConfiguration configuration, String path) {

    }
}
