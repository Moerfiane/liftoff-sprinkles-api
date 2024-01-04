package org.launchcode.sprinklespre.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Module extends AbstractEntity{

    @ManyToOne
    @JsonManagedReference
    private Course course;

    @ManyToMany
    private List<Tool> tools = new ArrayList<>();

    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    // What category is the main ingredient in it (vegetable, fruit, protein, etc)? Meant to make searching easier.
    private Set<String> tags;

    boolean isCompleted;
    //TODO: Add title
    //TODO: Add description
    //TODO: Add tools - model after skills
    //TODO: Add ingredients - model after skills
    //TODO: Add notes - ArrayList
    //TODO: Add steps - ArrayList

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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
