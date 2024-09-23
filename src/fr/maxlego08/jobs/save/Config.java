package fr.maxlego08.jobs.save;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobManager;
import fr.maxlego08.jobs.api.event.JobEvent;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permissible;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Config {

    public static boolean enableDebug = true;
    public static boolean enableDebugTime = false;
    public static List<Job> defaultJobs = new ArrayList<>();
    public static DecimalFormat decimalFormat;
    public static BossBar.Color progressionBarColor;
    public static BossBar.Overlay progressionBarOverlay;
    public static Map<String, Integer> jobLimitPermissions = new HashMap<>();
    public static Map<String, Boolean> eventInformations = new HashMap<>();

    /**
     * static Singleton instance.
     */
    private static volatile Config instance;


    /**
     * Private constructor for singleton.
     */
    private Config() {
    }

    /**
     * Return a singleton instance of Config.
     */
    public static Config getInstance() {
        // Double lock for thread safety.
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    public static int getJobLimit(Permissible permissible) {
        int limit = 0;
        for (String permission : jobLimitPermissions.keySet()) {
            if (permissible.hasPermission(permission)) {
                limit = Math.max(jobLimitPermissions.get(permission), limit);
            }
        }
        return limit;
    }

    public static boolean isEnable(JobEvent event) {
        return eventInformations.getOrDefault(event.getClass().getSimpleName(), true);
    }

    public void loadConfiguration(FileConfiguration configuration, ZJobsPlugin plugin) {

        JobManager jobManager = plugin.getJobManager();

        enableDebug = configuration.getBoolean("enable-debug");
        enableDebugTime = configuration.getBoolean("enable-debug-time");
        defaultJobs = configuration.getStringList("default-jobs").stream().map(jobManager::getJob).filter(Optional::isPresent).map(Optional::get).toList();
        decimalFormat = new DecimalFormat(configuration.getString("decimal-format", "#.##"));

        progressionBarColor = BossBar.Color.valueOf(configuration.getString("progression-bar.color", "WHITE").toUpperCase());
        progressionBarOverlay = BossBar.Overlay.valueOf(configuration.getString("progression-bar.overlay", "PROGRESS").toUpperCase());

        jobLimitPermissions = new HashMap<>();
        configuration.getMapList("jobs-limit-permissions").forEach(map -> {
            TypedMapAccessor accessor = new TypedMapAccessor((Map<String, Object>) map);
            jobLimitPermissions.put(accessor.getString("permission"), accessor.getInt("limit"));
        });

        eventInformations = new HashMap<>();
        var configurationSectionEvents = configuration.getConfigurationSection("events");
        if (configurationSectionEvents != null) {
            for (String eventName : configurationSectionEvents.getKeys(false)) {
                eventInformations.put(eventName, configurationSectionEvents.getBoolean(eventName, true));
            }
        }
    }

}
