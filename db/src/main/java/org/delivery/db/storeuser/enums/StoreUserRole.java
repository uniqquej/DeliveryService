package org.delivery.db.storeuser.enums;

public enum StoreUserRole {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN),
    MASTER(Authority.MASTER)
    ;

    private final String authority;
    StoreUserRole(String authority){
        this.authority = authority;
    }
    public String getAuthority(){
        return this.authority;
    }

    public static class Authority{
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String MASTER = "ROLE_MASTER";

    }
    public static StoreUserRole fromString(String role) {
        switch (role) {
            case "ROLE_USER":
                return USER;
            case "ROLE_ADMIN":
                return ADMIN;
            case "ROLE_MASTER":
                return MASTER;
        }
        return null;
    }
}
