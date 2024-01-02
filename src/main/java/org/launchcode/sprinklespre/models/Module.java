package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Module extends AbstractEntity{

    @ManyToOne
    private Course course;

    @ManyToMany
    private List<Tool> tools = new ArrayList<>();

    @ManyToMany
    private List<Ingredient> ingredients = new ArrayList<>();

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
