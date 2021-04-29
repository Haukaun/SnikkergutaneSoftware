package com.snikkergutane.project;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents a task in the project, and has a start date and end date.
 * It also contains a list of comments, and a list of imageURLs.
 */
public class Task {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private final ArrayList<Image> images;
    private ArrayList<Comment> comments;

    /**
     * Creates a new instance of the class.
     * @param name {@code String} name of the task.
     */
    public Task(String name) {
        this.name = name;
        this.comments = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    /**
     * Creates a new instance of the class.
     * @param name {@code String} name of the task.
     * @param startDate {@code LocalDate} start date of the task.
     * @param endDate {@code LocalDate} end date of the task.
     * @param description {@code String} description of the task.
     * @param imageUrls {@code List<String>} urls of the task's images.
     */
    public Task(String name, LocalDate startDate, LocalDate endDate, String description, List<String> imageUrls) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.comments = new ArrayList<>();
        this.images = new ArrayList<>();
        imageUrls.forEach(url -> this.images.add(new Image(url)));
    }

    /**
     * Returns the task's comments.
     * @return {@code List<Comment>} of the task's comments.
     */
    public List<Comment> getComments() {
        return this.comments;
    }

    public void addComment(Comment comment) {
        boolean commentExists = false;
        Iterator<Comment> it = this.comments.iterator();
        while (it.hasNext() && !commentExists) {
            Comment c = it.next();
            if (c.getCommentText().equals(comment.getCommentText()) && c.getDate().isEqual(comment.getDate()) && c.getUser().equals(comment.getUser())) {
                commentExists = true;
            }
        }
        if (!commentExists) {
            this.comments.add(comment);
        }
    }

    /**
     * Removes a comment from the task.
     * @param comment {@code Comment} to be removed.
     */
    public void removeComment(Comment comment) {
        boolean commentExists = false;
        Iterator<Comment> it = this.comments.iterator();
        while (it.hasNext() && !commentExists) {
            Comment c = it.next();
            if (c.equals(comment)) {
                commentExists = true;
                this.comments.remove(c);
            }
        }
    }

    /**
     * Returns a List of string arrays containing all information about the task, and its comments.
     * @return {@code List<String[]>} of all information about the task and its comments.
     */
    public List<String[]> getTaskAsStringArray() {
        List<String> taskInfo = new ArrayList<>(Arrays.asList("+" + this.name,"" + this.startDate,"" + this.endDate, this.description));
        this.images.forEach(image -> taskInfo.add(image.getUrl()));

        List<String[]> taskAsStringArray = new ArrayList<>();
        getComments().forEach(c -> taskAsStringArray.add(c.getCommentAsStringArray()));
        taskAsStringArray.add(taskInfo.toArray(new String[0]));

        return taskAsStringArray;
    }

    /**
     * Returns the description of the task.
     * @return {@code String} description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the task.
     * @param description {@code String} description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the task's start date.
     * @return {@code LocalDate} start date of the task.
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date for the task.
     * @param startDate {@code LocalDate} start date of the task.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the task's end date.
     * @return {@code LocalDate} end date of the task.
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date of the task.
     * @param endDate {@code LocalDate} end date of the task.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns a list of this tasks images.
     * @return {@code List<Image>} of the task's images.
     */
    public List<Image> getImages() {
        return this.images;
    }

    /**
     * Returns the name of the task.
     * @return {@code String} name of the task.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the task.
     * @param name {@code String} name of the task.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns true if the task is past it's end date, or false if not.
     * @return {@code true} if end date is before current day, or {@code false} if not.
     */
    public boolean isFinished() {
        if (null != this.endDate) {
            return this.endDate.isBefore(LocalDate.now());
        } else {
            return false;
        }
    }

    /**
     * Adds a new image from given url to the task's images if it doesn't already exist.
     * @param url {@code String} url of the image that is to be added.
     */
    public void addImage(String url) {
        boolean success = true;
        for (Image image : this.images) {
            if (image.getUrl().equals(url)) {
                success = false;
            }
        }
        if (success) {
            this.images.add(new Image(url));
        }
    }

    /**
     * Sets the task's images.
     * @param images {@code List<Image>} images of the task.
     */
    public void setImages(List<Image> images) {
        this.images.clear();
        this.images.addAll(images);
    }

    @Override
    public boolean equals(Object obj) {
        int similarity = 0;
        int i = 0;
        if (obj instanceof Task) {
            String[] objInfo = ((Task) obj).getTaskAsStringArray().get(((Task) obj).getTaskAsStringArray().size()-1);
            String[] thisInfo = getTaskAsStringArray().get(getTaskAsStringArray().size()-1);

            while (i < thisInfo.length) {
                if (thisInfo[i].equals(objInfo[i])) {
                    similarity++;
                }
                i++;
            }
        }
        double finalValue = 0;
        if (i != 0) {
            finalValue = (double) similarity / i;

        }
        return finalValue >= 0.8;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
