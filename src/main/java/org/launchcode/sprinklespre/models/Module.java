package org.launchcode.sprinklespre.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Module extends AbstractEntity{

    @ManyToOne
    @JsonManagedReference
    private Course course;

    boolean isCompleted;
    //TODO: Add description
    //TODO: Add tools - model after skills
    //TODO: Add ingredients - model after skills
    //TODO: Add notes - ArrayList
    //TODO: Add steps - ArrayList

    public Module() {}

    public Module(Course course) {
        this.course = course;
        this.isCompleted = false;
    }

    void finishModule() {
        this.isCompleted = true;
    }
}
