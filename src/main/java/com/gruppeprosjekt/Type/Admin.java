package com.gruppeprosjekt.Type;

import java.util.ArrayList;

/**
 * The Admin has full access to all the systems functions;
 * they can create, edit and delete projects, and add or edit
 * comments, dates and descriptions of tasks.
 * They can also edit the permissions of other users.
 */
public class Admin extends UserType {

    public Admin() {
        this.name = "Admin";
        this.permissions = new ArrayList<>();
        this.permissions.add("all");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ArrayList<String> getPermissions() {
        return this.permissions;
    }
}
