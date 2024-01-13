package org.launchcode.sprinklespre.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.sprinklespre.models.Course;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.data.CourseRepository;
import org.launchcode.sprinklespre.models.data.UserRepository;
import org.launchcode.sprinklespre.models.dto.CourseFormDTO;
import org.launchcode.sprinklespre.models.dto.EnrollDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@RestController
@CrossOrigin(origins = "http://localhost:5173/courses", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.GET})
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;
    private static final String userSessionKey = "user";

    //TODO: Complete display listCourses
    @GetMapping
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.GET} )
    public ResponseEntity<?> listCourses() {
        try {
            System.out.println("Attempting to fetch all courses");
            List<Course> courses = (List<Course>) courseRepository.findAll();
            System.out.println("Fetched " + courses.size() + " courses");
            return ResponseEntity.ok(courses);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error fetching courses"));
        }
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
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.GET} )
    @GetMapping("/view/{courseId}")
    public ResponseEntity<?> viewCourse(@PathVariable Integer courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()){
            return new ResponseEntity<>(courseOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/enroll")
    public ResponseEntity<EnrollDTO> displayEnroll() {
        System.out.println("Setting DTO");
        EnrollDTO enrollDTO = new EnrollDTO();
        return ResponseEntity.ok(enrollDTO);
    }

    //TODO: Update to ResponseEntity
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.OPTIONS} )
    @PostMapping("/enroll")
    public ResponseEntity<?> enrollInACourse(@RequestBody EnrollDTO enrollDTO) {
        System.out.println("Attemtping to enroll");
        Integer courseId = enrollDTO.getCourseId();
        Integer userId = enrollDTO.getUserId();
        System.out.println("courseId:" + courseId);
        System.out.println("userId:" + userId);

        // Fetch the user and course from the database
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        //make sure front end can receive the response type.
        if (userOpt.isEmpty() || courseOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Course not found");
        }

        User user = userOpt.get();
        Course course = courseOpt.get();

        //make sure front end can receive response
        // Check if user is already enrolled
        if (course.getUsers().contains(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already enrolled in the course");
        }
        System.out.println("course.getUsers: " + course.getUsers());
        // Perform the enrollment
        course.getUsers().add(user);
        System.out.println("course.getUsers: " + course.getUsers());
        courseRepository.save(course);
        System.out.println("course.getUsers: " + course.getUsers());
        user.getCourses().add(course);
        userRepository.save(user);

        // Return a success response
        return ResponseEntity.ok("Enrolled successfully in course " + course.getName());
    }

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


