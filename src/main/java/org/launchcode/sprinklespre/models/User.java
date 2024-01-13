package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity{
    @OneToMany(mappedBy = "createdBy")
    private List<Review> reviews= new ArrayList<>();
    @ManyToMany(mappedBy ="users")
    private List<Course> courses = new ArrayList<>();

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @NotNull
    private String role;


   // private boolean enabled = false;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password) {
        super();
        this.username = username;

        this.pwHash = encoder.encode(password);
        this.role = "user";
    }
    // Existing getters and setters

    public String getUsername() {
        return username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public boolean enrollInCourse(Course course) {
        if(!this.courses.contains(course)) {
            this.courses.add(course);
            return true;
        }
        return false;
    }

}
