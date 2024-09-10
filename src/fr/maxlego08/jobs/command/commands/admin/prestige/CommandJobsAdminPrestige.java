package fr.maxlego08.jobs.command.commands.admin.prestige;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobsAdminPrestige extends VCommand {

    public CommandJobsAdminPrestige(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_PRESTIGE);
        this.addSubCommand("Prestige");
        this.setDescription(Message.DESCRIPTION_ADMIN_PRESTIGE);
        this.addSubCommand(new CommandJobsAdminPrestigeAdd(plugin));
        this.addSubCommand(new CommandJobsAdminPrestigeRemove(plugin));
        this.addSubCommand(new CommandJobsAdminPrestigeSet(plugin));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {
        syntaxMessage();
        return CommandType.SUCCESS;
    }

}
