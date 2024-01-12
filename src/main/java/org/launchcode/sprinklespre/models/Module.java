package org.launchcode.sprinklespre.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name="course_Id")
    private Course course;


    private String description;
    
    private String tools;

    private String ingredients;

    // What category is the main ingredient in it (vegetable, fruit, protein, etc)? Meant to make searching easier.
    private String category;

    private String notes;

    private String steps;

    @ManyToMany
    private List<Tool> tools = new ArrayList<>();

    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

    // What category is the main ingredient in it (vegetable, fruit, protein, etc)? Meant to make searching easier.
    private Set<String> tags;

    boolean isCompleted;

    public Module() {}

    public Module(Course course, String description, String tools, String ingredients, String notes, String steps) {
        super();
        this.course = course;
        this.description = description;
        this.tools = tools;
        this.ingredients = ingredients;
        this.notes = notes;
        this.steps = steps;
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
