package fr.maxlego08.jobs;

import fr.maxlego08.jobs.command.commands.CommandJobs;
import fr.maxlego08.jobs.save.Config;
import fr.maxlego08.jobs.save.MessageLoader;
import fr.maxlego08.jobs.zcore.ZPlugin;
import fr.maxlego08.jobs.placeholder.LocalPlaceholder;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class ZJobsPlugin extends ZPlugin {

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("zjobs");

        this.preEnable();

        this.registerCommand("zjobs", new CommandJobs(this), "jobs");

        this.addSave(Config.getInstance());
        this.addSave(new MessageLoader(this));

        this.loadFiles();

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();

        this.postDisable();
    }

}
