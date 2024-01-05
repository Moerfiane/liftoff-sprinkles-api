package org.launchcode.sprinklespre.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseFormDTO {
    @NotNull
    @NotBlank
    private String courseTitle;

    @NotNull
    @NotBlank
    private String courseDescription;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    @Override public String toString() {
        return "CourseFormDTO: " +
                "title: " + courseTitle +
                "description: " + courseDescription;}
}
