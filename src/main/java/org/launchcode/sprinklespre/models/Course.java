package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course extends AbstractEntity{

    @OneToMany
    @JoinColumn(name = "course_id")
    private List<Module> modules = new ArrayList<>();

    @ManyToMany
    private List<User> users = new ArrayList<>();

    public Course() {}

    public Course(List<Module> modules, List<User> someUsers) {
        super();
        this.modules = modules;
        this.users.addAll(someUsers);
    }

    // Getters and setters
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

    public boolean enrollUser(User user) {
        if (!this.users.contains((user))) {
            this.users.add(user);
            return true;
        }
        return false;
    }

}
