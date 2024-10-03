package fr.maxlego08.jobs.command.commands.admin.reward;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;

public class CommandJobsAdminRewardSet extends VCommand {

    public CommandJobsAdminRewardSet(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_REWARD_SET);
        this.addSubCommand("set");
        this.setDescription(Message.DESCRIPTION_ADMIN_REWARD_SET);
        this.addRequireArg("player");
        this.addRequireArg("reward id", (a, b) -> plugin.getKnowRewards().stream().map(String::valueOf).toList());
        this.addRequireArg("true/false", (a, b) -> Arrays.asList("true", "false"));
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        OfflinePlayer offlinePlayer = this.argAsOfflinePlayer(0);
        int rewardId = this.argAsInteger(1);
        boolean rewardStatus = this.argAsBoolean(2);

        plugin.getJobManager().setReward(this.sender, offlinePlayer, rewardId, rewardStatus);

        return CommandType.SUCCESS;
    }

}
