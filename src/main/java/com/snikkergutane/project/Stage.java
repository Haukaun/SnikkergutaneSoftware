package com.snikkergutane.project;

import com.snikkergutane.project.task.Task;

import java.util.ArrayList;

/**
 * A single stage in a project,
 * containing a number of tasks to be finished.
 */
public class Stage extends TaskMasterClass {

    private ArrayList<Task> tasks;

    public Stage(String name) {
        super();
        setName(name);
    }

}
