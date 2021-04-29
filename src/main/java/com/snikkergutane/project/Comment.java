package com.snikkergutane.project;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a comment in a task.
 * A comment contains a date, a username, a comment text and may contain an image.
 */
public class Comment {
    private LocalDate date;
    private final String user;
    private String commentText;
    private Image image;
    private String imageUrl;

    /**
     * Creates a new instance of the class, without an image.
     * @param date {@code LocalDate} date of the comment.
     * @param user {@code String} name of the poster.
     * @param commentText {@code String} the comment text.
     */
    public Comment(LocalDate date, String user, String commentText) {
        this.date = date;
        this.user = user;
        this.commentText = commentText;
        this.image = null;
        this.imageUrl = null;
    }

    /**
     * Creates a new instance of the class, with an image.
     * @param date {@code LocalDate} date of the comment.
     * @param user {@code String} name of the poster.
     * @param commentText {@code String} the comment text.
     * @param imageUrl {@code String} url of the image.
     */
    public Comment(LocalDate date, String user, String commentText,String imageUrl) {
        this.date = date;
        this.user = user;
        this.commentText = commentText;
        this.imageUrl = imageUrl;
        this.image = new Image(imageUrl);
    }

    /**
     * Returns the date of the comment.
     * @return {@code LocalDate} date of the comment.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the name of the user who posted the comment.
     * @return {@code String} user of the comment.
     */
    public String getUser() {
        return user;
    }

    /**
     * Returns the comment's comment text.
     * @return {@code String} comment text of the comment.
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Sets the comment text of the comment.
     * @param commentText {@code String} comment text of the comment.
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * Returns the comment's image.
     * @return {@code Image} of the comment.
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Sets the comment's image.
     * @param image {@code Image} of the comment.
     */
    public void setImage(Image image) {

        this.imageUrl = image.getUrl();
        this.image = new Image(imageUrl);
    }

    /**
     * Generates a String array of the comments' details.
     * @return {@code String[]} comment information.
     */
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
                && this.image.getUrl().equals(emp.getImage().getUrl());
    }
}
