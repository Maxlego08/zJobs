package fr.maxlego08.jobs.economy;

import fr.maxlego08.jobs.api.economy.EconomyProvider;
import org.bukkit.OfflinePlayer;

public class EmptyProvider implements EconomyProvider {

    @Override
    public void depositMoney(OfflinePlayer offlinePlayer, double amount) {

    }
}
