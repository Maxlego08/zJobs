package fr.maxlego08.jobs.command.commands.admin.reward;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobsAdminReward extends VCommand {

    public CommandJobsAdminReward(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_REWARD);
        this.addSubCommand("reward");
        this.addSubCommand(new CommandJobsAdminRewardSet(plugin));
        this.setDescription(Message.DESCRIPTION_ADMIN_REWARD);
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {
        syntaxMessage();
        return CommandType.SUCCESS;
    }

}
