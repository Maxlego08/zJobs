package fr.maxlego08.jobs.save;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Config {

    public static boolean enableDebug = true;
    public static boolean enableDebugTime = false;
    public static List<Job> defaultJobs = new ArrayList<>();

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

    public void loadConfiguration(FileConfiguration configuration, ZJobsPlugin plugin) {

        JobManager jobManager = plugin.getJobManager();

        enableDebug = configuration.getBoolean("enable-debug");
        enableDebugTime = configuration.getBoolean("enable-debug-time");
        defaultJobs = configuration.getStringList("default-jobs").stream().map(jobManager::getJob).filter(Optional::isPresent).map(Optional::get).toList();
    }

}
