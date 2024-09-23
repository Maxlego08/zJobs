package fr.maxlego08.jobs.api.event.events;

import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.actions.ActionInfo;
import fr.maxlego08.jobs.api.event.CancelledJobEvent;
import fr.maxlego08.jobs.api.players.PlayerJob;
import fr.maxlego08.jobs.api.players.PlayerJobs;
import org.bukkit.entity.Player;

public class JobMoneyGainEvent extends CancelledJobEvent {

    private final Player player;
    private final PlayerJobs playerJobs;
    private final PlayerJob playerJob;
    private final Job job;
    private final ActionInfo<?> actionInfo;
    private double money;

    public JobMoneyGainEvent(Player player, PlayerJobs playerJobs, PlayerJob playerJob, Job job, ActionInfo<?> actionInfo, double money) {
        this.player = player;
        this.playerJobs = playerJobs;
        this.playerJob = playerJob;
        this.job = job;
        this.actionInfo = actionInfo;
        this.money = money;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerJobs getPlayerJobs() {
        return playerJobs;
    }

    public PlayerJob getPlayerJob() {
        return playerJob;
    }

    public Job getJob() {
        return job;
    }

    public ActionInfo<?> getActionInfo() {
        return actionInfo;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
