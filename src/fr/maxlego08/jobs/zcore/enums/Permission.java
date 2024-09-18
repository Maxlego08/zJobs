package fr.maxlego08.jobs.zcore.enums;

public enum Permission {

    ZJOBS_USE,
    ZJOBS_RELOAD,
    ZJOBS_JOIN,
    ZJOBS_LEAVE,
    ZJOBS_LEAVE_CONFIRM,
    ZJOBS_ADMIN_USE,
    ZJOBS_ADMIN_LEVEL,
    ZJOBS_ADMIN_LEVEL_ADD,
    ZJOBS_ADMIN_LEVEL_SET,
    ZJOBS_ADMIN_LEVEL_REMOVE,
    ZJOBS_ADMIN_EXPERIENCE,
    ZJOBS_ADMIN_EXPERIENCE_ADD,
    ZJOBS_ADMIN_EXPERIENCE_SET,
    ZJOBS_ADMIN_EXPERIENCE_REMOVE,
    ZJOBS_ADMIN_PRESTIGE,
    ZJOBS_ADMIN_BLOCK_INFO,
    ZJOBS_ADMIN_PRESTIGE_ADD,
    ZJOBS_ADMIN_PRESTIGE_SET,
    ZJOBS_ADMIN_PRESTIGE_REMOVE,
    ZJOBS_ADMIN_POINTS,
    ZJOBS_ADMIN_POINTS_ADD,
    ZJOBS_ADMIN_POINTS_SET,
    ZJOBS_ADMIN_POINTS_REMOVE,
    ZJOBS_ADMIN_POINTS_INFO,

    ;

    private final String permission;

    Permission() {
        this.permission = this.name().toLowerCase().replace("_", ".");
    }

    public String getPermission() {
        return permission;
    }

}
