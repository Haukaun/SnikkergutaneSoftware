package com.snikkergutane.project.task;

import com.snikkergutane.project.TaskMasterClass;

import java.time.LocalDate;

/**
 * A single task in a stage.
 */
public class Task extends TaskMasterClass {

    private String description;

    /**
     * First constructor.
     * @param name {@code String} name of the task.
     * @param description {@code String} description of the task.
     */
    public Task(String name, String description) {
        setName(name);
        this.description = description;
    }

    /**
     * Second constructor.
     * @param name {@code String} name of the task.
     * @param description {@code String} description of the task.
     * @param deadline {@code LocalDate} deadline for the task.
     */
    public Task(String name, String description, LocalDate deadline) {
        setName(name);
        this.description = description;
        setDeadline(deadline);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
