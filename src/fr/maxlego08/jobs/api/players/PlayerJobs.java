package fr.maxlego08.jobs.api.players;

import com.sun.source.doctree.SeeTree;
import fr.maxlego08.jobs.api.Job;
import fr.maxlego08.jobs.api.actions.ActionInfo;
import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PlayerJobs {

    UUID getUniqueId();

    List<PlayerJob> getJobs();

    boolean hasJob(Job job);

    void join(Job job);

    void leave(Job job);

    Optional<PlayerJob> get(Job job);

    Optional<PlayerJob> get(String jobId);

    int size();

    void action(Player player, Object target, JobActionType action);

    void process(Player player, PlayerJob playerJob, Job job, double experience, boolean initialCall, ActionInfo<?> actionInfo);

    void updateJobEconomies();

    long getPoints();

    void setPoints(long points);

    void addPoints(long points);

    void removePoints(long points);

    Set<String> getRewards();
}
