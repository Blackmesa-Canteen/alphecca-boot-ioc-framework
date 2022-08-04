package io.swen90007sm2.app.security.constant;

public enum AuthRole {

    CUSTOMER_ROLE (SecurityConstant.CUSTOMER_ROLE_NAME),
    ADMIN_ROLE (SecurityConstant.ADMIN_ROLE_NAME),
    HOTELIER_ROLE (SecurityConstant.HOTELIER_ROLE_NAME);

    private final String roleName;

    AuthRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
