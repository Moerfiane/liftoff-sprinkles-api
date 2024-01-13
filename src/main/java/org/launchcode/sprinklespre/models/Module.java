package org.launchcode.sprinklespre.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//TODO: Add title
//TODO: Add description
//TODO: Add tools - model after skills
//TODO: Add ingredients - model after skills
//TODO: Add notes - ArrayList
//TODO: Add steps - ArrayList

@Entity
public class Module extends AbstractEntity {

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "course_Id")
    private Course course;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String tools;
    @Column(columnDefinition = "TEXT")
    private String ingredients;

    // What category is the main ingredient in it (vegetable, fruit, protein, etc)? Meant to make searching easier.
    private String category;
    @Column(columnDefinition = "TEXT")
    private String notes;
    @Column(columnDefinition = "TEXT")
    private String steps;

    boolean isCompleted;

    public Module() {
    }

    public Module(Course course, String description, String tools, String ingredients, String category, String notes, String steps) {
        this.course = course;
        this.description = description;
        this.tools = tools;
        this.ingredients = ingredients;
        this.category = category;
        this.notes = notes;
        this.steps = steps;
    }

    public void finishModule() {
        this.isCompleted = true;
    }

    //Getters and setters

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    //NOTE: shouldn't completed modules be factored into user / course somehow? otherwise when it's marked completed it's marked for everyone
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + getId() +
                ", course=" + (course != null ? course.getId() : null) +
                ", description='" + description + '\'' +
                ", tools='" + tools + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", category='" + category + '\'' +
                ", notes='" + notes + '\'' +
                ", steps='" + steps + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
