package com.gruppeprosjekt.Type;

import java.util.ArrayList;

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
