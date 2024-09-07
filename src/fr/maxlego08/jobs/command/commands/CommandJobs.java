package fr.maxlego08.jobs.command.commands;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobs extends VCommand {

	public CommandJobs(ZJobsPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZJOBS_PERMISSION);
		this.addSubCommand(new CommandJobsReload(plugin));
	}

	@Override
	protected CommandType perform(ZJobsPlugin plugin) {
		syntaxMessage();
		return CommandType.SUCCESS;
	}

}
