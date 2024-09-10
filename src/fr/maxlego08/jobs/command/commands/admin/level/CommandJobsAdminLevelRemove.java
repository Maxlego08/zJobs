package fr.maxlego08.jobs.command.commands.admin.level;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.enums.AdminAction;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;

public class CommandJobsAdminLevelRemove extends VCommand {

    public CommandJobsAdminLevelRemove(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_LEVEL_REMOVE);
        this.addSubCommand("remove");
        this.setDescription(Message.DESCRIPTION_ADMIN_LEVEL_REMOVE);
        this.addRequireArg("player");
        this.addRequireArg("job", (a, b) -> plugin.getJobManager().getJobsName());
        this.addRequireArg("level", (a, b) -> Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        OfflinePlayer player = this.argAsOfflinePlayer(0);
        String jobName = this.argAsString(1);
        int level = this.argAsInteger(2);

        plugin.getJobManager().updatePlayerJobLevel(sender, player, jobName, level, AdminAction.REMOVE);

        return CommandType.SUCCESS;
    }

}
