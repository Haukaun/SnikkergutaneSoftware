package com.snikkergutane.project;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents a task in the project, and has a start date, finish date and/or deadline.
 * It also contains a list of comments, and a list of imageURLs.
 */
public class Task {

    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private final ArrayList<Image> images;
    private ArrayList<Comment> comments;

    public Task(String name) {
        this.name = name;
        this.comments = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public Task(String name, LocalDate startDate, LocalDate endDate, String description, List<String> imageUrls, int id) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.comments = new ArrayList<>();
        this.images = new ArrayList<>();
        imageUrls.forEach(url -> this.images.add(new Image(url)));
    }

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
        this.images.forEach(image -> taskInfo.add(image.getUrl()));

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

    public List<Image> getImages() {
        return this.images;
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
        for (Image image : this.images) {
            if (image.getUrl().equals(url)) {
                success = false;
            }
        }
        if (success) {
            this.images.add(new Image(url));
        }
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
                System.out.println(objInfo[i]);
                System.out.println(thisInfo[i]);
                i++;
            }
        }
        double finalValue = 0;
        if (i != 0) {
            System.out.println(similarity);
            System.out.println(i);
            finalValue = (double) similarity / i;
            System.out.println(finalValue);

        }
        return finalValue >= 0.8;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setImages(List<Image> images) {
        this.images.clear();
        this.images.addAll(images);
    }
}
