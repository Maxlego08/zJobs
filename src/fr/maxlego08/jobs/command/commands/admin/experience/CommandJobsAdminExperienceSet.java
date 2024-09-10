package fr.maxlego08.jobs.command.commands.admin.experience;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.enums.AdminAction;
import fr.maxlego08.jobs.api.enums.AttributeType;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;

public class CommandJobsAdminExperienceSet extends VCommand {

    public CommandJobsAdminExperienceSet(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_LEVEL_SET);
        this.addSubCommand("set");
        this.setDescription(Message.DESCRIPTION_ADMIN_EXPERIENCE_SET);
        this.addRequireArgOfflinePlayer();
        this.addRequireArg("job", (a, b) -> plugin.getJobManager().getJobsName());
        this.addRequireArg("experience", (a, b) -> Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        OfflinePlayer player = this.argAsOfflinePlayer(0);
        String jobName = this.argAsString(1);
        double experience = this.argAsDouble(2);

        plugin.getJobManager().updatePlayerJobAttribute(sender, player, jobName, experience, AdminAction.SET, AttributeType.EXPERIENCE);

        return CommandType.SUCCESS;
    }

}
