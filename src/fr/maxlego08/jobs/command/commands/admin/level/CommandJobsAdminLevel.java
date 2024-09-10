package fr.maxlego08.jobs.command.commands.admin.level;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobsAdminLevel extends VCommand {

    public CommandJobsAdminLevel(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_LEVEL);
        this.addSubCommand("level");
        this.setDescription(Message.DESCRIPTION_ADMIN_LEVEL);
        this.addSubCommand(new CommandJobsAdminLevelAdd(plugin));
        this.addSubCommand(new CommandJobsAdminLevelRemove(plugin));
        this.addSubCommand(new CommandJobsAdminLevelSet(plugin));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {
        syntaxMessage();
        return CommandType.SUCCESS;
    }

}
