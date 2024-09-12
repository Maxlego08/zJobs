package fr.maxlego08.jobs.api.storage;

import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.api.players.PlayerJobs;

import java.util.UUID;

public interface StorageManager {

    void load();

    PlayerJobs loadPlayerJobs(UUID uniqueId);

    void upsert(UUID uniqueId, PlayerJob playerJob, boolean force);

    void upsert(UUID uniqueId, int points);

    void deleteJob(UUID uniqueId, String fileName);

    void onDisable();
}
