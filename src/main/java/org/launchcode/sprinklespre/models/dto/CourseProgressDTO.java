package org.launchcode.sprinklespre.models.dto;

import java.util.List;

public class CourseProgressDTO {
    private String courseName;
    private double courseProgress;
    private List<ModuleProgressDTO> moduleProgress;  // Include module progress information

    public CourseProgressDTO(String courseName, double courseProgress) {
        this.courseName = courseName;
        this.courseProgress = courseProgress;
    }

    // Getters and setters

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(double courseProgress) {
        this.courseProgress = courseProgress;
    }

    public List<ModuleProgressDTO> getModuleProgress() {
        return moduleProgress;
    }

    public void setModuleProgress(List<ModuleProgressDTO> moduleProgress) {
        this.moduleProgress = moduleProgress;
    }
}
