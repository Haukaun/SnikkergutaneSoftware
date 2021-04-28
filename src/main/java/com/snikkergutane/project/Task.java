package com.snikkergutane.project;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents a task in the project, and has a start date, finish date and/or deadline.
 * It also contains a list of comments, and a list of imageURLs.
 */
public class Task {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private final ArrayList<String> imageURLs;
    private ArrayList<Comment> comments;

    public Task(String name) {
        this.name = name;
        this.comments = new ArrayList<>();
        this.imageURLs = new ArrayList<>();
    }

    public Task(String name, LocalDate startDate, LocalDate endDate, String description, List<String> imageUrls) {
        super();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.comments = new ArrayList<>();
        this.imageURLs = new ArrayList<>(imageUrls);
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void addComment(Comment comment) {
        boolean commentExists = false;
        Iterator<Comment> it = this.comments.iterator();
        while (it.hasNext() && !commentExists) {
            Comment c = it.next();
            if (c.equals(comment)) {
                commentExists = true;
            }
        }
        if (!commentExists) {
            this.comments.add(comment);
        }
    }

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

    public List<String[]> getTaskAsStringArray() {
        List<String> taskInfo = new ArrayList<>(Arrays.asList("+" + this.name,"" + this.startDate,"" + this.endDate, this.description));
        taskInfo.addAll(this.getImageURLs());

        List<String[]> taskAsStringArray = new ArrayList<>();
        getComments().forEach(c -> taskAsStringArray.add(c.getCommentAsStringArray()));
        taskAsStringArray.add(taskInfo.toArray(new String[0]));

        return taskAsStringArray;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public List<String> getImageURLs() {
        return this.imageURLs;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public boolean isFinished() {
        if (null != this.endDate) {
            return this.endDate.isBefore(LocalDate.now());
        } else {
            return false;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void addImage(String url) {
        boolean success = true;
        for (String imageUrl : this.imageURLs) {
            if (imageUrl.equals(url)) {
                success = false;
                break;
            }
        }
        if (success) {
            this.imageURLs.add(url);
        }
    }
}
