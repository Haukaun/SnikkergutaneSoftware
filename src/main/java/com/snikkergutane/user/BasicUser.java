package com.snikkergutane.user;

import java.util.ArrayList;

/**
 * The basic user is anyone working on an assigned project, under a project lead.
 * They can view their assigned project, including all of its stages and tasks,
 * and comment on any tasks in their project, edit their projected finish date,
 * and mark them as finished.
 */
public class BasicUser extends UserType {

    public BasicUser() {
        this.name = "Basic";
        this.permissions = new ArrayList<>();
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
