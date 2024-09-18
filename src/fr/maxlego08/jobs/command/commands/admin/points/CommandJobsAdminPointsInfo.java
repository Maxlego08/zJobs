package fr.maxlego08.jobs.command.commands.admin.points;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;
import org.bukkit.OfflinePlayer;

public class CommandJobsAdminPointsInfo extends VCommand {

    public CommandJobsAdminPointsInfo(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_POINTS_INFO);
        this.addSubCommand("info");
        this.setDescription(Message.DESCRIPTION_ADMIN_POINTS_INFO);
        this.addRequireArgOfflinePlayer();
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        OfflinePlayer player = this.argAsOfflinePlayer(0);
        plugin.getJobManager().showPoints(sender, player);

        return CommandType.SUCCESS;
    }

}
