package fr.maxlego08.jobs;

import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobManager;
import fr.maxlego08.jobs.api.enums.AdminAction;
import fr.maxlego08.jobs.api.enums.AttributeType;
import fr.maxlego08.jobs.api.enums.JobActionType;
import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.api.players.PlayerJobs;
import fr.maxlego08.jobs.api.storage.StorageManager;
import fr.maxlego08.jobs.players.ZPlayerJobs;
import fr.maxlego08.jobs.save.Config;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.utils.ElapsedTime;
import fr.maxlego08.jobs.zcore.utils.ZUtils;
import fr.maxlego08.jobs.zcore.utils.loader.Loader;
import fr.maxlego08.menu.api.scheduler.ZScheduler;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class ZJobManager extends ZUtils implements JobManager {

    private final ZJobsPlugin plugin;
    private final List<Job> jobs = new ArrayList<>();
    private final Map<UUID, PlayerJobs> players = new HashMap<>();

    public ZJobManager(ZJobsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void loadJobs() {

        File folder = new File(plugin.getDataFolder(), "jobs");
        if (!folder.exists()) {
            folder.mkdirs();
            this.plugin.saveResource("jobs/miner.yml", false);
        }

        this.jobs.clear();

        files(folder, file -> {
            Job job = loadJob(file);
            if (job != null) {
                this.jobs.add(job);
                this.plugin.getLogger().info("Job " + job.getName() + " loaded !");
            }
        });
    }

    @Override
    public void loadOfflinePlayer(UUID uuid, Consumer<PlayerJobs> consumer) {
        if (this.players.containsKey(uuid)) {
            consumer.accept(this.players.get(uuid));
        } else {
            this.plugin.getScheduler().runTaskAsynchronously(() -> consumer.accept(this.plugin.getStorageManager().loadPlayerJobs(uuid)));
        }
    }

    @Override
    public Job loadJob(File file) {
        Loader<Job> loader = new JobLoader(this.plugin, file);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return loader.load(configuration, "");
    }

    @Override
    public Optional<Job> getJob(String jobId) {
        return this.jobs.stream().filter(job -> job.getFileName().equalsIgnoreCase(jobId)).findFirst();
    }

    @Override
    public void loadPlayerJobs(Player player) {
        StorageManager storageManager = this.plugin.getStorageManager();
        ZScheduler scheduler = this.plugin.getScheduler();
        scheduler.runTaskAsynchronously(() -> {
            PlayerJobs playerJobs = storageManager.loadPlayerJobs(player.getUniqueId());
            this.players.put(player.getUniqueId(), playerJobs);

            Config.defaultJobs.forEach(job -> {
                if (!playerJobs.hasJob(job)) {
                    playerJobs.join(job);
                }
            });
        });
    }

    @Override
    public void playerQuit(Player player) {
        this.players.remove(player.getUniqueId());
    }

    @Override
    public void action(Player player, Object target, JobActionType action) {

        ElapsedTime elapsedTime = new ElapsedTime("Job Action " + action + " for " + player.getName() + " : " + target);
        elapsedTime.start();

        var optional = getPlayerJobs(player.getUniqueId());
        if (optional.isEmpty()) return;

        var playerJobs = optional.get();
        playerJobs.action(player, target, action);

        elapsedTime.endDisplay();
    }

    @Override
    public Optional<PlayerJobs> getPlayerJobs(UUID uuid) {
        return Optional.ofNullable(this.players.getOrDefault(uuid, null));
    }

    @Override
    public List<String> getJobsName() {
        return this.jobs.stream().map(Job::getFileName).toList();
    }

    @Override
    public List<String> getJobsName(CommandSender sender) {
        if (sender instanceof Player player) {
            var optional = getPlayerJobs(player.getUniqueId());
            return optional.map(playerJobs -> playerJobs.getJobs().stream().map(PlayerJob::getJobId).toList()).orElse(new ArrayList<>());
        }
        return new ArrayList<>();
    }

    @Override
    public void join(Player player, String name) {

        Optional<Job> optional = getJob(name);
        if (optional.isEmpty()) {
            message(player, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Job job = optional.get();
        if (!job.canJoin()) {
            message(player, Message.JOIN_ERROR_CANT, "%name%", name);
            return;
        }

        PlayerJobs playerJobs = this.players.computeIfAbsent(player.getUniqueId(), uuid -> new ZPlayerJobs(this.plugin, uuid, new ArrayList<>()));
        if (playerJobs.hasJob(job)) {
            message(player, Message.JOIN_ERROR_ALREADY, "%name%", name);
            return;
        }

        int limit = Config.getJobLimit(player);
        if (playerJobs.size() >= limit) {
            message(player, Message.JOIN_ERROR_LIMIT, "%max%", limit);
            return;
        }

        message(player, Message.JOIN_SUCCESS, "%name%", name);
        playerJobs.join(job);
    }

    @Override
    public void leave(Player player, String name, boolean confirm) {

        Optional<Job> optional = this.getJob(name);
        if (optional.isEmpty()) {
            message(player, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Job job = optional.get();

        if (!job.canLeave()) {
            message(player, Message.LEAVE_ERROR_CANT, "%name%", name);
            return;
        }

        var optionalPlayerJobs = getPlayerJobs(player.getUniqueId());

        if (optionalPlayerJobs.isEmpty()) {
            message(player, Message.LEAVE_ERROR, "%name%", name);
            return;
        }

        var playerJobs = optionalPlayerJobs.get();
        if (!playerJobs.hasJob(job)) {
            message(player, Message.LEAVE_ERROR, "%name%", name);
            return;
        }

        if (confirm) {
            message(player, Message.LEAVE_SUCCESS, "%name%", name);
            playerJobs.leave(job);
        } else {
            message(player, Message.LEAVE_SUCCESS_CONFIRM, "%name%", name);
        }
    }

    @Override
    public void updatePlayerJobAttribute(CommandSender sender, OfflinePlayer offlinePlayer, String name, double value, AdminAction action, AttributeType type) {
        Optional<Job> optional = this.getJob(name);
        if (optional.isEmpty()) {
            message(sender, Message.DOESNT_EXIST, "%name%", name);
            return;
        }

        Job job = optional.get();

        loadOfflinePlayer(offlinePlayer.getUniqueId(), playerJobs -> {

            var optionalPlayerJob = playerJobs.get(job);
            if (optionalPlayerJob.isEmpty()) {
                message(sender, Message.ADMIN_PLAYER_JOB, "%name%", name, "%player%", offlinePlayer.getName());
                return;
            }

            var playerJob = optionalPlayerJob.get();

            Message message = switch (type) {
                case LEVEL -> processLevelAction(playerJob, action, (int) value);
                case PRESTIGE -> processPrestigeAction(playerJob, action, (int) value);
                case EXPERIENCE -> processExperienceAction(playerJobs, playerJob, job, offlinePlayer, value, action);
            };

            message(sender, message, "%name%", job.getName(), "%value%", type == AttributeType.EXPERIENCE ? value : (int) value, "%player%", offlinePlayer.getName());
            this.plugin.getStorageManager().upsert(offlinePlayer.getUniqueId(), playerJob, true);
        });
    }

    private Message processLevelAction(PlayerJob playerJob, AdminAction action, int value) {
        return switch (action) {
            case ADD -> {
                playerJob.addLevel(value);
                yield Message.ADMIN_LEVEL_ADD;
            }
            case REMOVE -> {
                playerJob.removeLevel(value);
                yield Message.ADMIN_LEVEL_REMOVE;
            }
            case SET -> {
                playerJob.setLevel(value);
                yield Message.ADMIN_LEVEL_SET;
            }
        };
    }

    private Message processPrestigeAction(PlayerJob playerJob, AdminAction action, int value) {
        return switch (action) {
            case ADD -> {
                playerJob.addPrestige(value);
                yield Message.ADMIN_PRESTIGE_ADD;
            }
            case REMOVE -> {
                playerJob.removePrestige(value);
                yield Message.ADMIN_PRESTIGE_REMOVE;
            }
            case SET -> {
                playerJob.setPrestige(value);
                yield Message.ADMIN_PRESTIGE_SET;
            }
        };
    }

    private Message processExperienceAction(PlayerJobs playerJobs, PlayerJob playerJob, Job job, OfflinePlayer offlinePlayer, double experience, AdminAction action) {
        return switch (action) {
            case ADD -> {
                playerJobs.process(offlinePlayer.getPlayer(), playerJob, job, experience, false);
                yield Message.ADMIN_EXPERIENCE_ADD;
            }
            case REMOVE -> {
                playerJob.removeExperience(experience);
                yield Message.ADMIN_EXPERIENCE_REMOVE;
            }
            case SET -> {
                playerJob.setExperience(experience);
                yield Message.ADMIN_EXPERIENCE_SET;
            }
        };
    }
}
