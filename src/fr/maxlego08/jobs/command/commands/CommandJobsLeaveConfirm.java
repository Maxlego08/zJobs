package fr.maxlego08.jobs.command.commands;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobsLeaveConfirm extends VCommand {

    public CommandJobsLeaveConfirm(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_LEAVE_CONFIRM);
        this.addSubCommand("leaveconfirm");
        this.setDescription(Message.DESCRIPTION_LEAVE_CONFIRM);
        this.addRequireArg("name", (sender, b) -> plugin.getJobManager().getJobsName(sender));
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

		String name = this.argAsString(0);
		plugin.getJobManager().leave(this.player, name, true);

        return CommandType.SUCCESS;
    }

}
