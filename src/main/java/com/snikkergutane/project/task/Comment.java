package com.snikkergutane.project.task;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Comment {
    private LocalDate date;
    private final String user;
    private String commentText;
    private final List<String> imageUrls;

    public Comment(LocalDate date, String user, String commentText) {
        this.date = date;
        this.user = user;
        this.commentText = commentText;
        this.imageUrls = new ArrayList<>();
    }

    public Comment(LocalDate date, String user, String commentText, List<String> imageUrls) {
        this.date = date;
        this.user = user;
        this.commentText = commentText;
        this.imageUrls = imageUrls;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void addImage(Image image) {

        imageUrls.add(image.getUrl());
    }

    public void removeImage(Image image) {
        String deleteImage = null;
        for (String i : imageUrls) {
            if (i.equals(image.getUrl())) {
                deleteImage = i;
            }
        }
        if (deleteImage != null) {
            imageUrls.remove(deleteImage);
        }
    }

    public boolean containsImages() {
        return (!imageUrls.isEmpty());
    }

    public String[] getCommentAsStringArray() {
        List<String> commentInfo = new ArrayList<>(Arrays.asList("" + this.date, this.user, this.commentText));
        commentInfo.addAll(this.getImageUrls());
        return commentInfo.toArray(new String[0]);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Comment)) {
            return false;
        }
        Comment emp = (Comment) obj;
        return this.user.equals(emp.getUser())
                && this.date.isEqual(emp.getDate())
                && this.commentText.equals(emp.getCommentText())
                && this.imageUrls.size() == emp.getImageUrls().size();
    }
}
