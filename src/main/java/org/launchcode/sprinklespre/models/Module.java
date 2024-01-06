package org.launchcode.sprinklespre.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//TODO: Add title
//TODO: Add description
//TODO: Add tools - model after skills
//TODO: Add ingredients - model after skills
//TODO: Add notes - ArrayList
//TODO: Add steps - ArrayList
@Entity
public class Module extends AbstractEntity{

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="course_Id")
    private Course course;

    //TODO: Add data types  for tools, ingredients, and tags
    private String tools;

    private String ingredients;

    // What category is the main ingredient in it (vegetable, fruit, protein, etc)? Meant to make searching easier.
    private String category;

    private String notes;

    private String steps;

    boolean isCompleted;

    public Module() {}

    public Module(Course course) {
        this.course = course;
        this.isCompleted = false;
    }

    void finishModule() {
        this.isCompleted = true;
    }

    //Getters and setters

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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


    //NOTE: shouldn't completed modules be factored into user / course somehow? otherwise when it's marked completed it's marked for everyone
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}