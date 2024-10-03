package fr.maxlego08.jobs.zmenu.loader;

import fr.maxlego08.jobs.ZJobsPlugin;
import fr.maxlego08.jobs.zmenu.actions.ClaimRewardAction;
import fr.maxlego08.menu.api.loader.ActionLoader;
import fr.maxlego08.menu.api.requirement.Action;
import fr.maxlego08.menu.api.utils.TypedMapAccessor;

import java.io.File;

public class ClaimRewardLoader implements ActionLoader {

    private final ZJobsPlugin plugin;

    public ClaimRewardLoader(ZJobsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getKey() {
        return "zjobs_claim_reward";
    }

    @Override
    public Action load(String path, TypedMapAccessor accessor, File file) {
        String rewardId = accessor.getString("reward");
        if (!this.plugin.getKnowRewards().add(rewardId)) {
            this.plugin.getLogger().severe("Attention ! You have defined the ID " + rewardId + " several times!");
        }
        return new ClaimRewardAction(plugin, rewardId);
    }
}
