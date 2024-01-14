package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity{
    @ManyToMany(mappedBy ="users")
    private List<Course> courses = new ArrayList<>();

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @ManyToMany
    private List<Course> favoriteCourses = new ArrayList<Course>();

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password) {
        super();
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.favoriteCourses = new ArrayList<Course>();

    }
    // Existing getters and setters

    public String getUsername() {
        return username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getFavoriteCourses() {
        return favoriteCourses;
    }

    public void setFavoriteCourses(List<Course> favoriteCourses) {
        this.favoriteCourses = favoriteCourses;
    }

    public boolean enrollInCourse(Course course) {
        if(!this.courses.contains(course)) {
            this.courses.add(course);
            return true;
        }
        return false;
    }

    public boolean addFavorite(Course user) {
        if (!this.favoriteCourses.contains(user)) {
            this.favoriteCourses.add(user);
            return true;
        }
        return false;
    }

//    public boolean removeFavorite(User user) {
//        return this.favoritedBy.remove(user);
//    }

}
