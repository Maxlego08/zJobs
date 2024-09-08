package fr.maxlego08.jobs;

import fr.maxlego08.jobs.api.JobManager;
import fr.maxlego08.jobs.command.commands.CommandJobs;
import fr.maxlego08.jobs.placeholder.LocalPlaceholder;
import fr.maxlego08.jobs.save.Config;
import fr.maxlego08.jobs.save.MessageLoader;
import fr.maxlego08.jobs.zcore.ZPlugin;
import fr.maxlego08.menu.api.ButtonManager;
import fr.maxlego08.menu.api.InventoryManager;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class ZJobsPlugin extends ZPlugin {

    private final JobManager jobManager = new ZJobManager(this);
    private InventoryManager inventoryManager;
    private ButtonManager buttonManager;

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("zjobs");

        this.preEnable();
        this.saveDefaultConfig();

        this.registerCommand("zjobs", new CommandJobs(this), "jobs");

        this.inventoryManager = getProvider(InventoryManager.class);
        this.buttonManager = getProvider(ButtonManager.class);

        this.addSave(new MessageLoader(this));
        this.addListener(new JobListener(this));

        Config.getInstance().loadConfiguration(getConfig());
        this.jobManager.loadJobs();
        this.loadFiles();

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();

        this.postDisable();
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    @Override
    public void reloadFiles() {
        this.reloadConfig();
        Config.getInstance().loadConfiguration(getConfig());
        this.jobManager.loadJobs();
        super.reloadFiles();
    }
}
