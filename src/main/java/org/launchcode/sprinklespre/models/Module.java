package org.launchcode.sprinklespre.models;

import jakarta.persistence.ManyToOne;

public class Module extends AbstractEntity{

    @ManyToOne
    private Course course;

    boolean isCompleted;

    public Module() {}

    public Module(Course course) {
        this.course = course;
        this.isCompleted = false;
    }

    void finishModule() {
        this.isCompleted = true;
    }
}
