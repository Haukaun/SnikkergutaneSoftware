package com.snikkergutane.project;

import java.time.LocalDate;

public abstract class TaskMasterClass {
    private String name;
    private LocalDate deadline;
    private boolean finished;

    /**
     * Returns the name of the class.
     * @return {@code String} name of the class.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the task.
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the deadline of the task.
     * @return {@code LocalDate} deadline of the task.
     */
    public LocalDate getDeadline() {
        return this.deadline;
    }

    /**
     * Assigns a deadline for the task.
     * @param date {@code LocalDate} deadline of the task.
     */
    public void setDeadline(LocalDate date) {
        this.deadline = date;
    }

    /**
     * Is this task finished?
     * @return true if finished, false if not.
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * Sets the tasks finished state to true or false
     * @param finished {@code boolean} finished state of the project.
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
