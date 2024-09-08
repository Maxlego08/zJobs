package fr.maxlego08.jobs.api;

import java.io.File;

public interface JobManager {
    void loadJobs();

    Job loadJob(File file);
}
