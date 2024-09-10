package fr.maxlego08.jobs.api;

import fr.maxlego08.jobs.api.enums.AdminAction;
import fr.maxlego08.jobs.api.enums.AttributeType;
import fr.maxlego08.jobs.api.enums.JobActionType;
import fr.maxlego08.jobs.api.players.PlayerJobs;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface JobManager {
    void loadJobs();

    Job loadJob(File file);

    void loadPlayerJobs(Player player);

    Optional<Job> getJob(String jobId);

    void playerQuit(Player player);

    void action(Player player, Object target, JobActionType jobActionType);

    Optional<PlayerJobs> getPlayerJobs(UUID uuid);

    List<String> getJobsName();

    List<String> getJobsName(CommandSender sender);

    void join(Player player, String name);

    void leave(Player player, String name, boolean confirm);

    void updatePlayerJobAttribute(CommandSender sender, OfflinePlayer offlinePlayer, String name, double value, AdminAction action, AttributeType type);

    void loadOfflinePlayer(UUID uuid, Consumer<PlayerJobs> consumer);

    void updateJobEconomies();
}
