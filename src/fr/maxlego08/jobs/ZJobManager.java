package fr.maxlego08.jobs;

import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.JobManager;
import fr.maxlego08.jobs.zcore.utils.ZUtils;
import fr.maxlego08.jobs.zcore.utils.loader.Loader;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZJobManager extends ZUtils implements JobManager {

    private final ZJobsPlugin plugin;
    private final List<Job> jobs = new ArrayList<>();

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

        files(folder, file -> {
            Job job = loadJob(file);
            if (job != null) {
                jobs.add(job);
                this.plugin.getLogger().info("Job " + job.getName() + " loaded !");
            }
        });
    }

    @Override
    public Job loadJob(File file) {
        Loader<Job> loader = new JobLoader(this.plugin, file);
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return loader.load(configuration, "");
    }
}
