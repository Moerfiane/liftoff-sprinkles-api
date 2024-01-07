package org.launchcode.sprinklespre.models.dto;

public class ModuleProgressDTO {

    private String moduleName;
    private boolean isCompleted;

    public ModuleProgressDTO(String moduleName, boolean isCompleted) {
        this.moduleName = moduleName;
        this.isCompleted = isCompleted;
    }

    // Getters and setters

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
