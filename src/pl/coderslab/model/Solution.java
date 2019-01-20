package pl.coderslab.model;

import java.util.Date;

public class Solution {

    private int id;
    private String created;
    private String updated;
    private String description;
    private User user;
    private Exercise exercise;

    public Solution() {
    }

    public Solution(String created, String updated, String description, User user, Exercise exercise) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.user = user;
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
