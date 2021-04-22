package com.snikkergutane.project;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Comment {
    private LocalDate date;
    private final String user;
    private String commentText;
    private Image image;
    private String imageUrl;

    public Comment(LocalDate date, String user, String commentText) {
        this.date = date;
        this.user = user;
        this.commentText = commentText;
        this.image = null;
        this.imageUrl = null;
    }

    public Comment(LocalDate date, String user, String commentText,String imageUrl) {
        this.date = date;
        this.user = user;
        this.commentText = commentText;
        this.imageUrl = imageUrl;
        this.image = new Image(imageUrl);
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

    public Image getImage() {
        return this.image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(Image image) {

        this.imageUrl = image.getUrl();
        this.image = new Image(imageUrl);
    }

    public void removeImage() {
        this.image = null;
        this.imageUrl = null;
    }


    public String[] getCommentAsStringArray() {
        List<String> commentInfo = new ArrayList<>(Arrays.asList("" + this.date, this.user, this.commentText, this.imageUrl));
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
                && this.imageUrl.equals(emp.getImageUrl());
    }
}
