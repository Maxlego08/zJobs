package fr.maxlego08.jobs.command.commands.admin.points;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.enums.AdminAction;
import fr.maxlego08.jobs.api.enums.AttributeType;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;

public class CommandJobsAdminPointsSet extends VCommand {

    public CommandJobsAdminPointsSet(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_POINTS_SET);
        this.addSubCommand("set");
        this.setDescription(Message.DESCRIPTION_ADMIN_POINTS_SET);
        this.addRequireArgOfflinePlayer();
        this.addRequireArg("points", (a, b) -> Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        OfflinePlayer player = this.argAsOfflinePlayer(0);
        int points = this.argAsInteger(1);

        plugin.getJobManager().updatePoints(sender, player, points, AdminAction.SET);

        return CommandType.SUCCESS;
    }

}
