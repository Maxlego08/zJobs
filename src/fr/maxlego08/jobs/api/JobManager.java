package fr.maxlego08.jobs.api;

import fr.maxlego08.jobs.api.players.PlayerJobs;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

public interface JobManager {
    void loadJobs();

    Job loadJob(File file);

    void loadPlayerJobs(Player player);

    Optional<Job> getJob(String jobId);

    void playerQuit(Player player);

    void action(Player player, Object target, JobActionType jobActionType);

    Optional<PlayerJobs> getPlayerJobs(UUID uuid);
}
