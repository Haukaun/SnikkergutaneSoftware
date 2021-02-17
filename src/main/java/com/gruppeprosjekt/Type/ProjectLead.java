package com.gruppeprosjekt.Type;

import java.util.ArrayList;


/**
 * The project lead holds the primary responsibility of a project
 */
public class ProjectLead extends UserType {

    public ProjectLead() {
        this.name = "Project Lead";
        this.permissions = new ArrayList<>();
        this.permissions.add("readAll");
        this.permissions.add("editAssignedProject");
        this.permissions.add("finishAssignedProject");
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
