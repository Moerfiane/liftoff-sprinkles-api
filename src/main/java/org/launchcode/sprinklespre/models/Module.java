package org.launchcode.sprinklespre.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Module extends AbstractEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    boolean isCompleted;

    public Module() {}

    public Module(Course course) {
        super();
        this.course = course;
        this.isCompleted = false;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    void finishModule() {
        this.isCompleted = true;
    }
}
