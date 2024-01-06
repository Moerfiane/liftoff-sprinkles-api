package org.launchcode.sprinklespre.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.sprinklespre.models.Course;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.data.CourseRepository;
import org.launchcode.sprinklespre.models.data.UserRepository;
import org.launchcode.sprinklespre.models.dto.CourseFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;



@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.POST})
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;
    private static final String userSessionKey = "user";

    @GetMapping
    public ResponseEntity<?> listCourses() {
        List<Course> courses = (List<Course>) courseRepository.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

//    public User getUserFromSession(HttpSession session) {
//        Integer userId = (Integer) session.getAttribute(userSessionKey);
//        if (userId == null) {
//            return null;
//        }
//
//        Optional<User> user = userRepository.findById(userId);
//
//        if (user.isEmpty()) {
//            return null;
//        }
//
//        return user.get();
//    }
    @GetMapping("/view/{courseId}")
    public ResponseEntity<?> viewCourse(@PathVariable Integer courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()){
            return new ResponseEntity<>(courseOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO: Update to ResponseEntity
//    @PostMapping("/enroll/{courseId}")
//    public String enrollInACourse(@PathVariable Integer courseId, HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession();
//        User user = authenticationController.getUserFromSession(session);
//        if (user == null) {
//            return "redirect:/login";
//        }
//
//        Optional<Course> optionalCourse = courseRepository.findById(courseId);
//        if (optionalCourse.isEmpty()) {
//            model.addAttribute("enrollmentError", "Course was not found.");
//            return "redirect:/courses";
//        }
//
//        Course course = optionalCourse.get();
//
//        if (course.getUsers().contains(user)) {
//            model.addAttribute("enrollmentError", "You are already enrolled in this course.");
//            return "courses/view";
//        }
//
//        course.getUsers().add(user);
//        courseRepository.save(course);
//
//        user.getCourses().add(course);
//        userRepository.save(user);
//
//        return "courses/enroll";
//
//    }

    @GetMapping("/create")
    public ResponseEntity<CourseFormDTO> displayCourseForm() {
            CourseFormDTO courseFormDTO = new CourseFormDTO();
            return ResponseEntity.ok(courseFormDTO);
    }

    //Done: bypass via whitelist so i can start testing/adding data
    //TODO: undo whitelist once Suka gets login working
    //TODO: Reconstruct using abstract entity methods - need to delete unncessary fields
    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.POST} )
    public ResponseEntity<?> processAddCourseForm(@RequestBody CourseFormDTO courseFormDTO){
        //using blank constructor because current constructor takes users and modules
        Course newCourse = new Course();
        newCourse.setName(courseFormDTO.getCourseTitle());
        newCourse.setDescription(courseFormDTO.getCourseDescription());
        newCourse.setDifficulty(courseFormDTO.getCourseDifficulty());
        courseRepository.save(newCourse);
        return ResponseEntity.ok(Map.of("success", true, "message", "Course created", "courseId", newCourse.getId()));
    }

    @PostMapping("/unenroll/{courseId}")
    public String unenrollFromCourse (@PathVariable Integer courseId, HttpServletRequest request ,Model model) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        if (user == null) {
            return "redirect:/login";
        }

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            model.addAttribute("unenrollmentError", "Course was not found.");
            return "redirect:/courses";
        }

        Course course = optionalCourse.get();

        if (!course.getUsers().contains(user)) {
            model.addAttribute("unenrollmentError", "You are not enrolled in this course.");
            return "courses/view";
        }

        course.getUsers().remove(user);
        courseRepository.save(course);

        user.getCourses().remove(course);
        userRepository.save(user);

        return "redirect:/courses";
    }
}


