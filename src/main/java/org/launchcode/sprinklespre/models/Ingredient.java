package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient extends AbstractEntity{

    @ManyToMany
    private List<Module> modules = new ArrayList<>();

    // Amount of the ingredient that is needed for the module(recipe)
    private Double amountNeeded;

    private String unit;

    private String description;

    public Ingredient() {}

    //Getters and Setters
    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Double getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(Double amountNeeded) {
        this.amountNeeded = amountNeeded;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
