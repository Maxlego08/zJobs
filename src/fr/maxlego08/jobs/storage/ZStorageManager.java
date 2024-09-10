package fr.maxlego08.jobs.storage;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.api.players.PlayerJobs;
import fr.maxlego08.jobs.api.storage.StorageManager;
import fr.maxlego08.jobs.api.storage.StorageType;
import fr.maxlego08.jobs.dto.PlayerJobDTO;
import fr.maxlego08.jobs.migrations.CreateJobPlayerMigration;
import fr.maxlego08.jobs.players.ZPlayerJob;
import fr.maxlego08.jobs.players.ZPlayerJobs;
import fr.maxlego08.sarah.DatabaseConfiguration;
import fr.maxlego08.sarah.DatabaseConnection;
import fr.maxlego08.sarah.HikariDatabaseConnection;
import fr.maxlego08.sarah.MigrationManager;
import fr.maxlego08.sarah.MySqlConnection;
import fr.maxlego08.sarah.RequestHelper;
import fr.maxlego08.sarah.SqliteConnection;
import fr.maxlego08.sarah.database.DatabaseType;
import fr.maxlego08.sarah.logger.JULogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ZStorageManager implements StorageManager {

    private final ZJobsPlugin plugin;
    private final Map<UUID, Map<String, PlayerJob>> pendingUpdates = new ConcurrentHashMap<>();
    private final Map<UUID, Map<String, Long>> lastUpdateTime = new ConcurrentHashMap<>();
    private RequestHelper requestHelper;

    public ZStorageManager(ZJobsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load() {

        FileConfiguration configuration = plugin.getConfig();
        StorageType storageType = StorageType.valueOf(configuration.getString("storage-type", "SQLITE").toUpperCase());

        String tablePrefix = configuration.getString("database-configuration.table-prefix");
        String host = configuration.getString("database-configuration.host");
        int port = configuration.getInt("database-configuration.port");
        String user = configuration.getString("database-configuration.user");
        String password = configuration.getString("database-configuration.password");
        String database = configuration.getString("database-configuration.database");
        boolean debug = configuration.getBoolean("database-configuration.debug");

        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(tablePrefix, user, password, port, host, database, debug, storageType == StorageType.SQLITE ? DatabaseType.SQLITE : DatabaseType.MYSQL);
        DatabaseConnection connection = switch (storageType) {
            case MYSQL -> new MySqlConnection(databaseConfiguration);
            case SQLITE -> new SqliteConnection(databaseConfiguration, this.plugin.getDataFolder());
            case HIKARICP -> new HikariDatabaseConnection(databaseConfiguration);
        };
        this.requestHelper = new RequestHelper(connection, JULogger.from(plugin.getLogger()));

        if (!connection.isValid()) {
            plugin.getLogger().severe("Unable to connect to database!");
            Bukkit.getPluginManager().disablePlugin(plugin);
        } else {
            if (storageType == StorageType.SQLITE) {
                plugin.getLogger().info("The database connection is valid! (SQLITE)");
            } else {
                plugin.getLogger().info("The database connection is valid! (" + connection.getDatabaseConfiguration().getHost() + ")");
            }
        }

        MigrationManager.setMigrationTableName("zjobs_migrations");
        MigrationManager.setDatabaseConfiguration(databaseConfiguration);

        MigrationManager.registerMigration(new CreateJobPlayerMigration());

        MigrationManager.execute(connection, JULogger.from(this.plugin.getLogger()));

        startUpdateTask();
    }

    @Override
    public PlayerJobs loadPlayerJobs(UUID uniqueId) {
        List<PlayerJobDTO> playerJobDTOS = this.requestHelper.select("%prefix%jobs", PlayerJobDTO.class, table -> table.where("unique_id", uniqueId));
        return new ZPlayerJobs(this.plugin, uniqueId, playerJobDTOS.stream().map(ZPlayerJob::new).collect(Collectors.toList()));
    }

    @Override
    public void upsert(UUID uniqueId, PlayerJob playerJob, boolean force) {

        if (force) {
            executeUpsert(uniqueId, playerJob);
            return;
        }

        long currentTime = System.currentTimeMillis();

        Map<String, Long> jobLastUpdateTimes = lastUpdateTime.computeIfAbsent(uniqueId, k -> new ConcurrentHashMap<>());
        long lastTime = jobLastUpdateTimes.getOrDefault(playerJob.getJobId(), 0L);

        if (currentTime - lastTime < 5000) {
            Map<String, PlayerJob> jobs = pendingUpdates.computeIfAbsent(uniqueId, k -> new ConcurrentHashMap<>());
            jobs.put(playerJob.getJobId(), playerJob);
            return;
        }

        executeUpsert(uniqueId, playerJob);
        jobLastUpdateTimes.put(playerJob.getJobId(), currentTime);
    }

    private void executeUpsert(UUID uniqueId, PlayerJob playerJob) {
        this.plugin.getScheduler().runTaskAsynchronously(() -> {
            this.requestHelper.upsert("%prefix%jobs", table -> {
                table.uuid("unique_id", uniqueId).primary();
                table.string("job_id", playerJob.getJobId()).primary();
                table.bigInt("level", playerJob.getLevel());
                table.bigInt("prestige", playerJob.getPrestige());
                table.decimal("experience", playerJob.getExperience());
            });
        });
    }

    private void startUpdateTask() {
        this.plugin.getScheduler().runTaskTimerAsynchronously(100L, 100L, () -> {

            this.plugin.getJobManager().updateJobEconomies();

            long currentTime = System.currentTimeMillis();

            this.pendingUpdates.forEach((uniqueId, jobsMap) -> {

                Map<String, Long> jobLastUpdateTimes = lastUpdateTime.getOrDefault(uniqueId, new ConcurrentHashMap<>());
                Iterator<Map.Entry<String, PlayerJob>> iterator = jobsMap.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, PlayerJob> entry = iterator.next();
                    String jobId = entry.getKey();
                    PlayerJob playerJob = entry.getValue();

                    long lastTime = jobLastUpdateTimes.getOrDefault(jobId, 0L);

                    if (currentTime - lastTime >= 5000) {
                        executeUpsert(uniqueId, playerJob);
                        jobLastUpdateTimes.put(jobId, currentTime);
                        iterator.remove();
                    }
                }
            });
        });
    }

    @Override
    public void deleteJob(UUID uniqueId, String jobId) {
        this.plugin.getScheduler().runTaskAsynchronously(() -> {
            this.requestHelper.delete("%prefix%jobs", table -> {
                table.where("unique_id", uniqueId).primary();
                table.where("job_id", jobId);
            });

            Map<String, PlayerJob> jobs = this.pendingUpdates.get(uniqueId);
            if (jobs != null) {
                jobs.remove(jobId);
            }
        });
    }

    @Override
    public void onDisable() {

    }
}