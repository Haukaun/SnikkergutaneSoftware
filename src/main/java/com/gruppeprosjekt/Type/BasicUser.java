package com.gruppeprosjekt.Type;

import java.util.ArrayList;

public class BasicUser extends UserType {

    public BasicUser() {
        this.name = "Basic";
        this.permissions = new ArrayList<String>();
        this.permissions.add("readAssignedProjects");
        this.permissions.add("addComment");
        this.permissions.add("finishTask");
        this.permissions.add("setTaskDate");
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
