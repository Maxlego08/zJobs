package fr.maxlego08.jobs.command.commands.admin.prestige;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.enums.AdminAction;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;

public class CommandJobsAdminPrestigeSet extends VCommand {

    public CommandJobsAdminPrestigeSet(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_PRESTIGE_SET);
        this.addSubCommand("set");
        this.setDescription(Message.DESCRIPTION_ADMIN_PRESTIGE_SET);
        this.addRequireArg("player");
        this.addRequireArg("job", (a, b) -> plugin.getJobManager().getJobsName());
        this.addRequireArg("prestige", (a, b) -> Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        OfflinePlayer player = this.argAsOfflinePlayer(0);
        String jobName = this.argAsString(1);
        int prestige = this.argAsInteger(2);

        plugin.getJobManager().updatePlayerJobPrestige(sender, player, jobName, prestige, AdminAction.SET);

        return CommandType.SUCCESS;
    }

}
