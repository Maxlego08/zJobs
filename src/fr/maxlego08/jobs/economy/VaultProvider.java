package fr.maxlego08.jobs.economy;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.api.economy.EconomyProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;

public class VaultProvider implements EconomyProvider {

    private final ZJobsPlugin plugin;
    private final Economy economy;

    public VaultProvider(ZJobsPlugin plugin) {
        this.plugin = plugin;
        this.economy = plugin.getProvider(Economy.class);
    }

    @Override
    public void depositMoney(OfflinePlayer offlinePlayer, double amount) {
        this.plugin.getScheduler().runTask(offlinePlayer.getLocation(), () -> this.economy.depositPlayer(offlinePlayer, amount));
    }
}
