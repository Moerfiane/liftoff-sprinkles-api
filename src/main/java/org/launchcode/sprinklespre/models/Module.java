package org.launchcode.sprinklespre.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Module extends AbstractEntity{

    @ManyToOne
    @JsonBackReference
    private Course course;

    @ManyToMany
    private List<Tool> tools = new ArrayList<>();

    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    // What category is the main ingredient in it (vegetable, fruit, protein, etc)? Meant to make searching easier.
    private List<String> tags = new ArrayList<>();

    boolean isCompleted;
    //TODO: Add title
    //TODO: Add description
    //TODO: Add tools - model after skills
    //TODO: Add ingredients - model after skills
    //TODO: Add notes - ArrayList
    //TODO: Add steps - ArrayList

    public Module() {}

    public Module(Course course, List<Tool> tools, List<Ingredient> ingredients, List<String> tags) {
        this.course = course;
        this.tools = tools;
        this.ingredients = ingredients;
        this.tags = tags;
        this.isCompleted = false;
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

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
