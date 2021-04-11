package com.snikkergutane.user;

import java.util.ArrayList;


/**
 * The project lead holds the primary responsibility of a project.
 * Project Lead can view all projects,
 * and edit and mark as finished the project(s) they are assigned to.
 */
public class ProjectLead extends UserType {

    /**
     * Instantiates a new Project lead.
     */
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
