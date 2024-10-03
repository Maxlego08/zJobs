package fr.maxlego08.jobs.command.commands.admin;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.command.commands.admin.experience.CommandJobsAdminExperience;
import fr.maxlego08.jobs.command.commands.admin.level.CommandJobsAdminLevel;
import fr.maxlego08.jobs.command.commands.admin.points.CommandJobsAdminPoints;
import fr.maxlego08.jobs.command.commands.admin.prestige.CommandJobsAdminPrestige;
import fr.maxlego08.jobs.command.commands.admin.reward.CommandJobsAdminReward;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobsAdmin extends VCommand {

    public CommandJobsAdmin(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_USE);
        this.addSubCommand("admin", "a");
        this.setDescription(Message.DESCRIPTION_RELOAD);
        this.addSubCommand(new CommandJobsAdminLevel(plugin));
        this.addSubCommand(new CommandJobsAdminExperience(plugin));
        this.addSubCommand(new CommandJobsAdminPrestige(plugin));
        this.addSubCommand(new CommandJobsAdminPoints(plugin));
        this.addSubCommand(new CommandJobsAdminInfo(plugin));
        this.addSubCommand(new CommandJobsAdminReward(plugin));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {
        syntaxMessage();
        return CommandType.SUCCESS;
    }

}
