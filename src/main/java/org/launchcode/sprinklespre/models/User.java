package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity{

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private List<Module> completedModules = new ArrayList<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    public String getUsername() {
        return username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public void completeModule(Module module) {
        completedModules.add(module);
    }

    // Method for finding user's current progress in a specific course
    public double getCourseProgress(Course course) {
        double numberofCompletedModules = 0;

        for (Module completedModule : completedModules) {
            if (completedModule.getCourse().equals(course)) {
                numberofCompletedModules++;
            }
        }

        return numberofCompletedModules / course.getTotalModules() * 100;
    }
}
