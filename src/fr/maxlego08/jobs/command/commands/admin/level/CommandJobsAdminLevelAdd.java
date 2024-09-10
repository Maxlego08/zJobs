package fr.maxlego08.jobs.command.commands.admin.level;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.enums.AdminAction;
import fr.maxlego08.jobs.api.enums.AttributeType;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;

public class CommandJobsAdminLevelAdd extends VCommand {

    public CommandJobsAdminLevelAdd(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_LEVEL_ADD);
        this.addSubCommand("add");
        this.setDescription(Message.DESCRIPTION_ADMIN_LEVEL_ADD);
        this.addRequireArgOfflinePlayer();
        this.addRequireArg("job", (a, b) -> plugin.getJobManager().getJobsName());
        this.addRequireArg("level", (a, b) -> Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        OfflinePlayer player = this.argAsOfflinePlayer(0);
        String jobName = this.argAsString(1);
        int level = this.argAsInteger(2);

        plugin.getJobManager().updatePlayerJobAttribute(sender, player, jobName, level, AdminAction.ADD, AttributeType.LEVEL);

        return CommandType.SUCCESS;
    }

}
