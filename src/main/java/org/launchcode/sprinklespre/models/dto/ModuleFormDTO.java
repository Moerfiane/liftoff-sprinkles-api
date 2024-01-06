package org.launchcode.sprinklespre.models.dto;

import org.launchcode.sprinklespre.models.Course;

public class ModuleFormDTO {
    //TODO: Check that this should be Course data type and not courseId
    private Course courseId;

    private String tools;

    private String ingredients;

    private String category;

    private String notes;

    private String steps;

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

    @Override public String toString() {
        return "ModuleFormDTO{" +
                "course=" + course +
                ", tools='" + tools + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", category='" + category + '\'' +
                ", notes='" + notes + '\'' +
                ", steps='" + steps + '\'' +
                '}';
    }
}
