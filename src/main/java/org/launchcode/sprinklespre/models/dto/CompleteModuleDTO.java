package org.launchcode.sprinklespre.models.dto;

public class CompleteModuleDTO {
    private Integer moduleId;

    private Integer userId;

    public Integer getModuleId() {
        return moduleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
