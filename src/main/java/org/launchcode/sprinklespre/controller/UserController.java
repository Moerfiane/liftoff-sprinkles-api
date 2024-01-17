package org.launchcode.sprinklespre.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.launchcode.sprinklespre.models.Course;
import org.launchcode.sprinklespre.models.Module;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.data.ModuleRepository;
import org.launchcode.sprinklespre.models.dto.CompleteModuleDTO;
import org.launchcode.sprinklespre.models.dto.CourseProgressDTO;
import org.launchcode.sprinklespre.models.dto.FavoriteDTO;
import org.launchcode.sprinklespre.models.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.launchcode.sprinklespre.models.data.UserRepository;
import org.launchcode.sprinklespre.models.data.CourseRepository;

import java.util.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:5173/dashboard", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.GET})
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private AuthenticationController authenticationController;

    @GetMapping
    public ResponseEntity<UserDTO> getUserId(){
        System.out.println("Setting DTO");
        UserDTO userDTO = new UserDTO();
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    public ResponseEntity<?> getEnrolledCourses(@RequestBody UserDTO userDTO, HttpServletRequest request, Model model) {
        System.out.println("Attempting to fetch enrolled courses");
        System.out.println(userDTO.getUserId());

        Optional<User> userOptional = userRepository.findById(userDTO.getUserId());

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            List<CourseProgressDTO> enrolledCourses = user.getCourseProgressForUser();

            return ResponseEntity.ok(Map.of("success", true, "enrolledCourses", enrolledCourses));
        } else {
            return ResponseEntity.ok(Map.of("success", false, "message", "Failed to fetch courses"));
        }
    }


    @GetMapping("/complete-module")
    public ResponseEntity<CompleteModuleDTO> processModuleData(@RequestParam Integer moduleId) {

        CompleteModuleDTO completeModuleDTO = new CompleteModuleDTO();

        return ResponseEntity.ok(completeModuleDTO);
    }


    @PostMapping("/complete-module")
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    public ResponseEntity<?> completeModule(@RequestBody CompleteModuleDTO completeModuleDTO) {

        Optional<User> userOptional = userRepository.findById(completeModuleDTO.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Module> moduleOptional = moduleRepository.findById(completeModuleDTO.getModuleId());

            if (moduleOptional.isPresent()) {
                Module module = moduleOptional.get();
                user.completeModule(module);
                userRepository.save(user);
                return ResponseEntity.ok(Map.of("success", true));
            } else {
                return ResponseEntity.ok(Map.of("success", false, "message", "Did not complete module successfully"));
            }
        }
        return ResponseEntity.ok(Map.of("success", false, "message", "User not found"));
    }

    @GetMapping("/favorite")
    public ResponseEntity<FavoriteDTO> displayFavoriteCourses() {
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        return ResponseEntity.ok(favoriteDTO);
    }

    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    @PostMapping("/favorite")
    public ResponseEntity<?> favoriteCourse(@RequestBody FavoriteDTO favoriteDTO) {
        Integer courseId = favoriteDTO.getCourseId();
        Integer userId = favoriteDTO.getUserId();

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (userOpt.isEmpty() || courseOpt.isEmpty()) {
            return ResponseEntity.ok(Map.of("success", false, "message", "User or course not found"));
        }

        User user = userOpt.get();
        Course course = courseOpt.get();
        List<Integer> favoriteCourses = user.getFavoriteCourseIds();
        System.out.println(favoriteCourses);

        if (favoriteCourses == null) {
            // If the list is null, initialize it as an empty list
            favoriteCourses = new ArrayList<>();
            user.setFavoriteCourseIds(favoriteCourses);
        }

        if (favoriteCourses.contains(courseId)) {
            return ResponseEntity.ok(Map.of("success", false, "message", "You have already favorited this course."));
        }
        // set list
        user.getFavoriteCourseIds().add(courseId);
        userRepository.save(user);

        List<Course> courseObjs = (List<Course>) courseRepository.findAllById(favoriteCourses);

        // for each id in favoriteCourses
        // set favoriteCourseObjects to equal courseRepository
        // List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);

        return ResponseEntity.ok(Map.of("success", true, "message", "Course favorited successfully: " + course.getName(), "data", courseObjs));
    }

}
