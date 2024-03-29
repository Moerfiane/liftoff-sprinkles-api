package org.launchcode.sprinklespre.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//TODO: Consider how best to add image url: where will this be stored, how will it be accessed?
//TODO: Add cuisine (e.g., vegetarian, gluten-free) - model after skills
@Entity
public class Course extends AbstractEntity{

//Added FetchType.EAGER and CascadeType.ALL which seemed to solve deserialization issues on reload
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
//    @JoinColumn(name = "course_id")
    @JsonManagedReference
    private List<Module> modules = new ArrayList<>();

    //Ignoring for deserialization because we won't need this data
    @JsonIgnore
    @ManyToMany
    private List<User> users = new ArrayList<>();

    //Description of the course
    private String description;

    //How difficult is the course (1-beginner, 2-intermediate, 3-advanced)
    private Integer difficulty;

    public Course() {}

    public Course(List<Module> modules, String description, Integer difficulty) {
        super();
        this.modules = modules;
        this.description = description;
        this.difficulty = difficulty;
    }

//    public Course(List<Module> modules, List<User> someUsers) {
//        super();
//        this.modules = modules;
//        this.users.addAll(someUsers);
//    }

    // Getters and setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Course progress based on how many completed modules vs how many total in course
    public int getTotalModules() {
        return modules.size();
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

//    public boolean enrollUser(User user) {
//        if (!this.users.contains((user))) {
//            this.users.add(user);
//            return true;
//        }
//        return false;
//    }

}
