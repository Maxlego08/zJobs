package fr.maxlego08.jobs.api.economy;

import org.bukkit.OfflinePlayer;

public interface EconomyProvider {

    void depositMoney(OfflinePlayer offlinePlayer, double amount);

}
