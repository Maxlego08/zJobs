package fr.maxlego08.jobs.command.commands;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;

public class CommandJobsJoin extends VCommand {

    public CommandJobsJoin(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_JOIN);
        this.addSubCommand("join", "rejoindre");
        this.setDescription(Message.DESCRIPTION_JOIN);
        this.addRequireArg("name", (a, b) -> plugin.getJobManager().getJobsName());
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        String name = this.argAsString(0);
        plugin.getJobManager().join(this.player, name);

        return CommandType.SUCCESS;
    }

}
