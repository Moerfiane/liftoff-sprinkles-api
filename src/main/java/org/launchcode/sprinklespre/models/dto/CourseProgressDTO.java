package org.launchcode.sprinklespre.models.dto;

import java.util.List;

public class CourseProgressDTO {
    private String courseName;
    private double progress;
    private List<ModuleProgressDTO> moduleProgress;  // Include module progress information

    public CourseProgressDTO(String courseName, double progress, List<ModuleProgressDTO> moduleProgress) {
        this.courseName = courseName;
        this.progress = progress;
        this.moduleProgress = moduleProgress;
    }

    // Getters and setters

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public List<ModuleProgressDTO> getModuleProgress() {
        return moduleProgress;
    }

    public void setModuleProgress(List<ModuleProgressDTO> moduleProgress) {
        this.moduleProgress = moduleProgress;
    }
}
