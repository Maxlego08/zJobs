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

    ;

    private final String permission;

    Permission() {
        this.permission = this.name().toLowerCase().replace("_", ".");
    }

    public String getPermission() {
        return permission;
    }

}
