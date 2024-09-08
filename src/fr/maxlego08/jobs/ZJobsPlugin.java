package fr.maxlego08.jobs;

import fr.maxlego08.jobs.api.JobManager;
import fr.maxlego08.jobs.api.storage.StorageManager;
import fr.maxlego08.jobs.command.commands.CommandJobs;
import fr.maxlego08.jobs.component.PaperComponent;
import fr.maxlego08.jobs.placeholder.LocalPlaceholder;
import fr.maxlego08.jobs.save.Config;
import fr.maxlego08.jobs.save.MessageLoader;
import fr.maxlego08.jobs.storage.ZStorageManager;
import fr.maxlego08.jobs.zcore.ZPlugin;
import fr.maxlego08.menu.api.ButtonManager;
import fr.maxlego08.menu.api.InventoryManager;
import fr.maxlego08.menu.api.scheduler.ZScheduler;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class ZJobsPlugin extends ZPlugin {

    private final JobManager jobManager = new ZJobManager(this);
    private final StorageManager storageManager = new ZStorageManager(this);
    private InventoryManager inventoryManager;
    private ButtonManager buttonManager;
    private final PaperComponent paperComponent = new PaperComponent();

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

        this.jobManager.loadJobs();
        Config.getInstance().loadConfiguration(getConfig(), this);
        this.loadFiles();

        this.storageManager.load();

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
        this.jobManager.loadJobs();
        Config.getInstance().loadConfiguration(getConfig(), this);
        super.reloadFiles();
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public ZScheduler getScheduler() {
        return this.inventoryManager.getScheduler();
    }

    public PaperComponent getPaperComponent() {
        return paperComponent;
    }
}
