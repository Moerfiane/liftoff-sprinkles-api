package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import org.launchcode.sprinklespre.models.dto.CourseProgressDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity{

    @OneToMany(mappedBy = "createdBy")
    private List<Review> reviews= new ArrayList<>();

    //Should not be initialized as per Carrie's video class 18 20:55
    @ManyToMany(mappedBy ="users")
    private List<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "user_completed_modules",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<Module>completedModulesList = new ArrayList<>();

    @NotNull
    private String username;

    @NotNull
    private String pwHash;
    @ManyToMany
    private List<Course> favoriteCourses = new ArrayList<Course>();
    @NotNull
    private String role;


   // private boolean enabled = false;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {    }

    public User(String username, String password) {
        super();
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.favoriteCourses = new ArrayList<Course>();
        this.role = "user";
    }
    // Getters and setters

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

    // Method to mark a module as completed by the user
    void completeModule(Module module) {

        if (!module.isCompleted) {
            module.finishModule();
            completedModulesList.add(module);
        }
    }

    // Method to get the user's progress in a course
    double getProgress(Course course) {
        return (double) completedModulesList.size() / course.getTotalModules() * 100;
    }

    public List<CourseProgressDTO> getCourseProgressForUser(User user) {

        List<Course> userCourses = user.getCourses();
        List<CourseProgressDTO> progressList = new ArrayList<>();


        for (Course course : userCourses) {
            double progress = getProgress(course);

            progressList.add(new CourseProgressDTO(course.getName(), progress));
        }

        return progressList;
    }
}
