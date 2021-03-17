package com.snikkergutane.user;

import java.util.ArrayList;

/**
 * The users user type regulates the functions they have access to.
 */
public abstract class UserType {
    String name;
    ArrayList<String> permissions;

    /**
     * Returns the name of the user type.
     * @return {@code String} name of the user type.
     */
    public abstract String getName();

    /**
     * Returns the list of permissions for the user type.
     * @return {@code ArrayList<String>} permissions for the user type.
     */
    public abstract ArrayList<String> getPermissions();
}
