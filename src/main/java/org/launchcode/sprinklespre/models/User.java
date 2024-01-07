package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import org.launchcode.sprinklespre.models.dto.CourseProgressDTO;
import org.launchcode.sprinklespre.models.dto.ModuleProgressDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.stream.Collectors;

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

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private List<Module> completedModules = new ArrayList<>();


    public User() {}

    public User(String username, String password) {
        super();
        this.username = username;
        this.pwHash = encoder.encode(password);
    }
    // Existing getters and setters

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
//    public double getCourseProgress(Course course) {
//        double numberofCompletedModules = 0;
//
//        for (Module completedModule : completedModules) {
//            if (completedModule.getCourse().equals(course)) {
//                numberofCompletedModules++;
//            }
//        }
//
//        return numberofCompletedModules / course.getTotalModules() * 100;
//    }

    public List<CourseProgressDTO> getCourseProgressForUser(User user) {
        List<CourseProgressDTO> progressList = new ArrayList<>();

        for (Course course : user.getCourses()) {
            double progress = user.getCourseProgress(course);
            List<ModuleProgressDTO> moduleProgress = getModuleProgressForCourse(user, course);

            progressList.add(new CourseProgressDTO(course.getName(), progress, moduleProgress));
        }

        return progressList;
    }

    private List<ModuleProgressDTO> getModuleProgressForCourse(User user, Course course) {
        List<ModuleProgressDTO> moduleProgressList = new ArrayList<>();

        for (Module module : course.getModules()) {
            boolean isModuleCompleted = user.getCompletedModules(course).contains(module);
            moduleProgressList.add(new ModuleProgressDTO(module.getName(), isModuleCompleted));
        }

        return moduleProgressList;
    }

    public double getCourseProgress(Course course) {
        List<Module> completedModules = getCompletedModules(course);
        int totalModules = course.getModules().size();

        // Calculate the progress as a percentage
        if (totalModules > 0) {
            double completedModuleCount = completedModules.size();
            return (completedModuleCount / totalModules) * 100.0;
        } else {
            return 0.0; // If there are no modules, progress is 0%
        }
    }

    private List<Module> getCompletedModules(Course course) {
        return completedModules.stream()
                .filter(completedModule -> completedModule.getCourse().equals(course))
                .collect(Collectors.toList());
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
