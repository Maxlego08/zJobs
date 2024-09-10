package fr.maxlego08.jobs.zcore.enums;

public enum Permission {

    ZJOBS_PERMISSION,
    ZJOBS_RELOAD,
    ZJOBS_JOIN,
    ZJOBS_LEAVE,

    ;

    private final String permission;

    Permission() {
        this.permission = this.name().toLowerCase().replace("_", ".");
    }

    public String getPermission() {
        return permission;
    }

}
