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
    private final Map<UUID, Long> lastUpdateTime = new ConcurrentHashMap<>();
    private final Map<UUID, PlayerJob> pendingUpdates = new ConcurrentHashMap<>();
    private DatabaseConnection connection;
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
        this.connection = switch (storageType) {
            case MYSQL -> new MySqlConnection(databaseConfiguration);
            case SQLITE -> new SqliteConnection(databaseConfiguration, this.plugin.getDataFolder());
            case HIKARICP -> new HikariDatabaseConnection(databaseConfiguration);
        };
        this.requestHelper = new RequestHelper(this.connection, JULogger.from(plugin.getLogger()));

        if (!this.connection.isValid()) {
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

        MigrationManager.execute(this.connection, JULogger.from(this.plugin.getLogger()));

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
        long lastTime = lastUpdateTime.getOrDefault(uniqueId, 0L);

        if (currentTime - lastTime < 5000) {
            pendingUpdates.put(uniqueId, playerJob);
            return;
        }

        executeUpsert(uniqueId, playerJob);
        lastUpdateTime.put(uniqueId, currentTime);
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
            long currentTime = System.currentTimeMillis();

            Iterator<Map.Entry<UUID, PlayerJob>> iterator = pendingUpdates.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<UUID, PlayerJob> entry = iterator.next();
                UUID uniqueId = entry.getKey();
                PlayerJob playerJob = entry.getValue();

                long lastTime = lastUpdateTime.getOrDefault(uniqueId, 0L);

                if (currentTime - lastTime >= 5000) {
                    executeUpsert(uniqueId, playerJob);
                    lastUpdateTime.put(uniqueId, currentTime);
                    iterator.remove();
                }
            }
        });
    }

    @Override
    public void deleteJob(UUID uniqueId, String jobId) {
        this.plugin.getScheduler().runTaskAsynchronously(() -> this.requestHelper.delete("%prefix%jobs", table -> {
            table.where("unique_id", uniqueId).primary();
            table.where("job_id", jobId);
        }));
    }
}
