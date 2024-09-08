package fr.maxlego08.jobs.save;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static boolean enableDebug = true;
    public static boolean enableDebugTime = false;

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

    public void loadConfiguration(FileConfiguration configuration) {
        enableDebug = configuration.getBoolean("enable-debug");
        enableDebugTime = configuration.getBoolean("enable-debug-time");
    }

}
