package com.snikkergutane.project;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskMasterClass {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean finished;
    private final ArrayList<String> imageURLs;

    protected TaskMasterClass() {
        this.imageURLs = new ArrayList<>();
    }

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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the deadline of the task.
     * @return {@code LocalDate} deadline of the task.
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Returns the start date of the task.
     * @return {@code LocalDate} start date of the task.
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    public ArrayList<String> getImageURLs() {
        return this.imageURLs;
    }

    public void addImage(String imageURL) {
        this.imageURLs.add(imageURL);
    }
    /**
     * Sets the start date of the task.
     * @param date {@code LocalDate} start date of the task.
     */
    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    /**
     * Assigns a deadline for the task.
     * @param date {@code LocalDate} deadline of the task.
     */
    public void setEndDate(LocalDate date) {
        this.endDate = date;
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

    /**
     * Returns the {@code String} description of the task.
     * @return {@code String} description of the task.
     */
    public String getDescription() {
        return this.description;
    }

}
