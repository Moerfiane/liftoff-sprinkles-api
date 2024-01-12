package org.launchcode.sprinklespre.models.dto;

import org.launchcode.sprinklespre.models.Course;

public class ModuleFormDTO {
    //TODO: Check that this should be Course data type and not courseId
    private Integer courseId;
    private String description;

    private String tools;

    private String ingredients;

    private String category;

    private String notes;

    private String steps;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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

    @Override public String toString() {
        return "ModuleFormDTO{" +
                "course=" + courseId +
                ", tools='" + tools + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", category='" + category + '\'' +
                ", notes='" + notes + '\'' +
                ", steps='" + steps + '\'' +
                '}';
    }
}
