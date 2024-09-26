package fr.maxlego08.jobs;

import fr.maxlego08.jobs.api.JobManager;
import fr.maxlego08.jobs.api.enums.JobActionType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Container;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class JobListener implements Listener {

    private final ZJobsPlugin plugin;
    private final JobManager jobManager;
    private final NamespacedKey playerKey;

    public JobListener(ZJobsPlugin plugin) {
        this.plugin = plugin;
        this.jobManager = plugin.getJobManager();
        this.playerKey = new NamespacedKey(plugin, "job_player_uuid");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.jobManager.loadPlayerJobs(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.jobManager.playerQuit(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Material material = block.getType();

        if (!(block.getBlockData() instanceof Ageable)) {

            if (this.plugin.getBlockHook().isTracked(block)) return;

            this.jobManager.action(player, material, JobActionType.BLOCK_BREAK);

        } else if (block.getBlockData() instanceof Ageable ageable && ((material == Material.SUGAR_CANE || material == Material.KELP || material == Material.BAMBOO) || ageable.getAge() == ageable.getMaximumAge())) {

            this.jobManager.action(player, material, JobActionType.FARMING);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Material material = block.getType();

        this.jobManager.action(player, material, JobActionType.BLOCK_PLACE);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent event) {

        Player player = event.getPlayer();
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH && event.getCaught() instanceof Item item) {

            ItemStack itemStack = item.getItemStack();
            this.jobManager.action(player, itemStack.getType(), JobActionType.FISHING);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAnimalTame(EntityTameEvent event) {

        LivingEntity animal = event.getEntity();

        if (animal.isDead()) return;

        if (event.getOwner() instanceof Player player && player.isOnline()) {
            this.jobManager.action(player, animal.getType(), JobActionType.TAME);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAnimalTame(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        if (entity.getKiller() != null) {
            this.jobManager.action(entity.getKiller(), entity.getType(), JobActionType.KILL_ENTITY);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEnchantItem(EnchantItemEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory instanceof EnchantingInventory enchantingInventory) {
            ItemStack itemStack = enchantingInventory.getItem();
            if (itemStack == null) return;

            Player player = event.getEnchanter();
            this.jobManager.action(player, event, JobActionType.ENCHANT);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        var inventory = event.getInventory();
        if (event.getWhoClicked() instanceof Player player) {
            if ((inventory.getType() == InventoryType.BREWING || inventory.getType() == InventoryType.FURNACE || inventory.getType() == InventoryType.BLAST_FURNACE || inventory.getType() == InventoryType.SMOKER) && inventory.getHolder() instanceof Container container) {

                var block = container.getBlock();
                if (block.getType() == Material.BREWING_STAND || block.getType() == Material.FURNACE || block.getType() == Material.BLAST_FURNACE || block.getType() == Material.SMOKER) {
                    var containerState = (Container) block.getState();

                    var playerUUID = player.getUniqueId();

                    var persistentDataContainer = containerState.getPersistentDataContainer();
                    persistentDataContainer.set(playerKey, PersistentDataType.STRING, playerUUID.toString());

                    containerState.update();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBrew(BrewEvent event) {
        var brewerInventory = event.getContents();
        var block = brewerInventory.getHolder().getBlock();

        if (block.getType() == Material.BREWING_STAND) {
            var brewingStand = (BrewingStand) block.getState();
            var container = brewingStand.getPersistentDataContainer();

            if (container.has(playerKey, PersistentDataType.STRING)) {
                var uuidString = container.get(playerKey, PersistentDataType.STRING);
                var playerUUID = UUID.fromString(uuidString);

                Player player = Bukkit.getPlayer(playerUUID);
                if (player != null) {
                    this.jobManager.action(player, event, JobActionType.BREW);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.FURNACE || block.getType() == Material.BLAST_FURNACE || block.getType() == Material.SMOKER) {
            Furnace furnace = (Furnace) block.getState();
            PersistentDataContainer container = furnace.getPersistentDataContainer();

            if (container.has(playerKey, PersistentDataType.STRING)) {
                var uuidString = container.get(playerKey, PersistentDataType.STRING);
                var playerUUID = UUID.fromString(uuidString);

                Player player = Bukkit.getPlayer(playerUUID);
                if (player != null) {
                    this.jobManager.action(player, event.getResult().getType(), JobActionType.SMELT);
                }
            }
        }
    }
}
