package fr.maxlego08.jobs.zcore.utils;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class EntityTypeToEggConverter {

    public static Material getSpawnEgg(EntityType entityType) {
        return switch (entityType) {
            case ALLAY -> Material.ALLAY_SPAWN_EGG;
            case AXOLOTL -> Material.AXOLOTL_SPAWN_EGG;
            case BAT -> Material.BAT_SPAWN_EGG;
            case BEE -> Material.BEE_SPAWN_EGG;
            case BLAZE -> Material.BLAZE_SPAWN_EGG;
            case CAT -> Material.CAT_SPAWN_EGG;
            case CAVE_SPIDER -> Material.CAVE_SPIDER_SPAWN_EGG;
            case CHICKEN -> Material.CHICKEN_SPAWN_EGG;
            case COD -> Material.COD_SPAWN_EGG;
            case COW -> Material.COW_SPAWN_EGG;
            case CREEPER -> Material.CREEPER_SPAWN_EGG;
            case DOLPHIN -> Material.DOLPHIN_SPAWN_EGG;
            case DONKEY -> Material.DONKEY_SPAWN_EGG;
            case DROWNED -> Material.DROWNED_SPAWN_EGG;
            case ELDER_GUARDIAN -> Material.ELDER_GUARDIAN_SPAWN_EGG;
            case ENDERMAN -> Material.ENDERMAN_SPAWN_EGG;
            case ENDERMITE -> Material.ENDERMITE_SPAWN_EGG;
            case EVOKER -> Material.EVOKER_SPAWN_EGG;
            case FOX -> Material.FOX_SPAWN_EGG;
            case FROG -> Material.FROG_SPAWN_EGG;
            case GHAST -> Material.GHAST_SPAWN_EGG;
            case GLOW_SQUID -> Material.GLOW_SQUID_SPAWN_EGG;
            case GOAT -> Material.GOAT_SPAWN_EGG;
            case GUARDIAN -> Material.GUARDIAN_SPAWN_EGG;
            case HOGLIN -> Material.HOGLIN_SPAWN_EGG;
            case HORSE -> Material.HORSE_SPAWN_EGG;
            case HUSK -> Material.HUSK_SPAWN_EGG;
            case ILLUSIONER -> Material.TOTEM_OF_UNDYING;
            case IRON_GOLEM -> Material.IRON_GOLEM_SPAWN_EGG;
            case LLAMA -> Material.LLAMA_SPAWN_EGG;
            case MAGMA_CUBE -> Material.MAGMA_CUBE_SPAWN_EGG;
            case MUSHROOM_COW -> Material.MOOSHROOM_SPAWN_EGG;
            case MULE -> Material.MULE_SPAWN_EGG;
            case OCELOT -> Material.OCELOT_SPAWN_EGG;
            case PANDA -> Material.PANDA_SPAWN_EGG;
            case PARROT -> Material.PARROT_SPAWN_EGG;
            case PHANTOM -> Material.PHANTOM_SPAWN_EGG;
            case PIG -> Material.PIG_SPAWN_EGG;
            case PIGLIN -> Material.PIGLIN_SPAWN_EGG;
            case PIGLIN_BRUTE -> Material.PIGLIN_BRUTE_SPAWN_EGG;
            case PILLAGER -> Material.PILLAGER_SPAWN_EGG;
            case POLAR_BEAR -> Material.POLAR_BEAR_SPAWN_EGG;
            case PUFFERFISH -> Material.PUFFERFISH_SPAWN_EGG;
            case RABBIT -> Material.RABBIT_SPAWN_EGG;
            case RAVAGER -> Material.RAVAGER_SPAWN_EGG;
            case SALMON -> Material.SALMON_SPAWN_EGG;
            case SHEEP -> Material.SHEEP_SPAWN_EGG;
            case SHULKER -> Material.SHULKER_SPAWN_EGG;
            case SILVERFISH -> Material.SILVERFISH_SPAWN_EGG;
            case SKELETON -> Material.SKELETON_SPAWN_EGG;
            case SKELETON_HORSE -> Material.SKELETON_HORSE_SPAWN_EGG;
            case SLIME -> Material.SLIME_SPAWN_EGG;
            case SNOWMAN -> Material.SNOW_BLOCK;
            case SPIDER -> Material.SPIDER_SPAWN_EGG;
            case SQUID -> Material.SQUID_SPAWN_EGG;
            case STRAY -> Material.STRAY_SPAWN_EGG;
            case STRIDER -> Material.STRIDER_SPAWN_EGG;
            case TADPOLE -> Material.TADPOLE_SPAWN_EGG;
            case TRADER_LLAMA -> Material.TRADER_LLAMA_SPAWN_EGG;
            case TROPICAL_FISH -> Material.TROPICAL_FISH_SPAWN_EGG;
            case TURTLE -> Material.TURTLE_SPAWN_EGG;
            case VEX -> Material.VEX_SPAWN_EGG;
            case VILLAGER -> Material.VILLAGER_SPAWN_EGG;
            case VINDICATOR -> Material.VINDICATOR_SPAWN_EGG;
            case WANDERING_TRADER -> Material.WANDERING_TRADER_SPAWN_EGG;
            case WARDEN -> Material.WARDEN_SPAWN_EGG;
            case WITCH -> Material.WITCH_SPAWN_EGG;
            case WITHER_SKELETON -> Material.WITHER_SKELETON_SPAWN_EGG;
            case WOLF -> Material.WOLF_SPAWN_EGG;
            case ZOGLIN -> Material.ZOGLIN_SPAWN_EGG;
            case ZOMBIE -> Material.ZOMBIE_SPAWN_EGG;
            case ZOMBIE_HORSE -> Material.ZOMBIE_HORSE_SPAWN_EGG;
            case ZOMBIE_VILLAGER -> Material.ZOMBIE_VILLAGER_SPAWN_EGG;
            case ZOMBIFIED_PIGLIN -> Material.ZOMBIFIED_PIGLIN_SPAWN_EGG;
            default -> Material.EGG;
        };
    }
}
