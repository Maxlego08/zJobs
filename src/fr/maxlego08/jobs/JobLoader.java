package fr.maxlego08.jobs;

import fr.maxlego08.jobs.actions.BrewAction;
import fr.maxlego08.jobs.actions.EnchantmentAction;
import fr.maxlego08.jobs.actions.EntityAction;
import fr.maxlego08.jobs.actions.MaterialAction;
import fr.maxlego08.jobs.actions.TagAction;
import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobAction;
import fr.maxlego08.jobs.api.JobReward;
import fr.maxlego08.jobs.api.enums.JobActionType;
import fr.maxlego08.jobs.zcore.utils.TagRegistry;
import fr.maxlego08.jobs.zcore.utils.loader.Loader;
import fr.maxlego08.menu.api.enchantment.Enchantments;
import fr.maxlego08.menu.api.enchantment.MenuEnchantment;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionType;

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
        boolean canJoin = configuration.getBoolean("can-join", true);
        boolean canLeave = configuration.getBoolean("can-leave", true);
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

        return new ZJob(name, file.getName().replace(".yml", ""), baseExperience, maxLevels, maxPrestiges, formula, jobActions, jobRewards, canJoin, canLeave);
    }

    private List<JobAction<?>> loadActions(YamlConfiguration configuration) {
        List<JobAction<?>> jobActions = new ArrayList<>();

        configuration.getMapList("actions").forEach(map -> {
            TypedMapAccessor accessor = new TypedMapAccessor((Map<String, Object>) map);
            double experience = accessor.getDouble("experience", 0);
            double money = accessor.getDouble("money", 0);
            try {

                JobActionType jobActionType = JobActionType.valueOf(accessor.getString("type").toUpperCase());
                if (jobActionType.isMaterial()) {

                    if (accessor.contains("material")) {
                        Material material = Material.valueOf(accessor.getString("material").toUpperCase());
                        jobActions.add(new MaterialAction(material, experience, money, jobActionType));
                    } else if (accessor.contains("tag")) {
                        Tag<Material> tag = TagRegistry.getTag(accessor.getString("tag").toUpperCase());
                        jobActions.add(new TagAction(tag, experience, money, jobActionType));
                    } else {
                        plugin.getLogger().severe("Impossible to find the tag or material for BLOCK BREAK in file " + file.getAbsolutePath());
                    }

                } else if (jobActionType.isEntityType()) {

                    EntityType entityType = EntityType.valueOf(accessor.getString("entity").toUpperCase());
                    jobActions.add(new EntityAction(entityType, experience, money));

                } else if (jobActionType == JobActionType.ENCHANT) {

                    Enchantments enchantments = plugin.getInventoryManager().getEnchantments();
                    String enchantmentName = accessor.getString("enchantment");
                    String materialName = accessor.getString("material", null);

                    Material material = materialName == null ? null : Material.valueOf(materialName.toUpperCase());
                    Enchantment enchantment = enchantmentName == null ? null : enchantments.getEnchantments(enchantmentName).map(MenuEnchantment::getEnchantment).orElse(null);
                    int minimumLevel = accessor.getInt("minimumLevel", 0);
                    int minimumCost = accessor.getInt("minimumCost", 0);

                    jobActions.add(new EnchantmentAction(material, experience, money, enchantment, minimumLevel, minimumCost));

                } else if (jobActionType == JobActionType.BREW) {

                    String potionName = accessor.getString("potion-type", null);
                    String potionMaterialName = accessor.getString("potion-material", "POTION");
                    String ingredientName = accessor.getString("ingredient", null);

                    PotionType potionType = potionName == null ? null : PotionType.valueOf(potionName.toUpperCase());
                    Material material = ingredientName == null ? null : Material.valueOf(ingredientName.toUpperCase());
                    Material potionMaterial = Material.valueOf(potionMaterialName.toUpperCase());

                    jobActions.add(new BrewAction(potionType, experience, money, potionMaterial, material));
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
