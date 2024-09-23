package fr.maxlego08.jobs.api.event;

import org.bukkit.event.Cancellable;

public class CancelledJobEvent extends JobEvent implements Cancellable {

    private boolean cancelled;

    /**
     * @return the cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @param cancelled the canceled to set
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
