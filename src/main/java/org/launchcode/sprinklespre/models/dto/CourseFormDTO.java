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
}
