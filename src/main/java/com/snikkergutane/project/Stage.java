package com.snikkergutane.project;

import com.snikkergutane.project.task.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A single stage in a project,
 * containing a number of tasks to be finished.
 */
public class Stage extends TaskMasterClass {

    private ArrayList<Comment> comments;

    public Stage(String name) {
        super();
        setName(name);
        this.comments = new ArrayList<>();
    }

    public Stage(String name, LocalDate startDate, LocalDate endDate, String description, List<String> imageUrls) {
        super();
        super.setName(name);
        super.setStartDate(startDate);
        super.setEndDate(endDate);
        super.setDescription(description);
        this.comments = new ArrayList<>();
        imageUrls.forEach(super::addImage);
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

    public List<String[]> getStageAsStringArray() {
        List<String> stageInfo = new ArrayList<>(Arrays.asList("+" + this.getName(),"" + this.getStartDate(),"" + this.getEndDate(), this.getDescription()));
        stageInfo.addAll(this.getImageURLs());

        List<String[]> stageAsStringArray = new ArrayList<>();
        getComments().forEach(c -> stageAsStringArray.add(c.getCommentAsStringArray()));
        stageAsStringArray.add(stageInfo.toArray(new String[0]));

        return stageAsStringArray;
    }


}
