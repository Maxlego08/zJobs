package fr.maxlego08.jobs.command.commands.admin;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.command.VCommand;
import fr.maxlego08.jobs.zcore.enums.Message;
import fr.maxlego08.jobs.zcore.enums.Permission;
import fr.maxlego08.jobs.zcore.utils.commands.CommandType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

public class CommandJobsAdminInfo extends VCommand {

    public CommandJobsAdminInfo(ZJobsPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.ZJOBS_ADMIN_BLOCK_INFO);
        this.addSubCommand("blockinfo", "bi");
        this.setDescription(Message.DESCRIPTION_ADMIN_PRESTIGE);
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(ZJobsPlugin plugin) {

        Block block = this.player.getTargetBlock(null, 64);
        if (block.getType().isAir()) {
            message(sender, Message.ADMIN_BLOCKINFO_ERROR);
            return CommandType.DEFAULT;
        }

        Location location = block.getLocation();
        message(sender, Message.ADMIN_BLOCKINFO_MATERIAL, "%world%", location.getWorld().getName(), "%x%", location.getBlockX(), "%y%", location.getBlockY(), "%z%", location.getBlockZ(), "%material%", block.getType());

        var blockData = block.getBlockData();
        if (blockData instanceof Ageable ageable) {
            message(sender, Message.ADMIN_BLOCKINFO_AGE, "%age%", ageable.getAge(), "%maxAge%", ageable.getMaximumAge());
        }

        message(sender, Message.ADMIN_BLOCKINFO_FOOTER);

        return CommandType.SUCCESS;
    }

}
