package fr.maxlego08.jobs.command.commands.admin.experience;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobsAdminExperience extends VCommand {

    public CommandJobsAdminExperience(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_EXPERIENCE);
        this.addSubCommand("experience", "exp", "xp");
        this.setDescription(Message.DESCRIPTION_ADMIN_EXPERIENCE);
        this.addSubCommand(new CommandJobsAdminExperienceAdd(plugin));
        this.addSubCommand(new CommandJobsAdminExperienceRemove(plugin));
        this.addSubCommand(new CommandJobsAdminExperienceSet(plugin));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {
        syntaxMessage();
        return CommandType.SUCCESS;
    }

}
