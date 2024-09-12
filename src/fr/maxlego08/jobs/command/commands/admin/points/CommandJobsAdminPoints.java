package fr.maxlego08.jobs.command.commands.admin.points;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobsAdminPoints extends VCommand {

    public CommandJobsAdminPoints(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_POINTS);
        this.addSubCommand("points");
        this.setDescription(Message.DESCRIPTION_ADMIN_POINTS);
        this.addSubCommand(new CommandJobsAdminPointsAdd(plugin));
        this.addSubCommand(new CommandJobsAdminPointsRemove(plugin));
        this.addSubCommand(new CommandJobsAdminPointsSet(plugin));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {
        syntaxMessage();
        return CommandType.SUCCESS;
    }

}
