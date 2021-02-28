package com.retailShop.page;

import com.retailShop.entity.User;

public class Permission {
    private static User user = Logger.loggedUser;

    public static boolean userCan(String action, String what) {
        if (user.getUserRoleByRoleid().getId() == 4) {
            return true;
        }
        for (com.retailShop.entity.Permission permission : user.getUserRoleByRoleid().getPermissionsById()) {
            if (permission.getAction().equals(action) && permission.getName().equals(what)) {
                return true;
            }
        }
        return false;
    }
}
