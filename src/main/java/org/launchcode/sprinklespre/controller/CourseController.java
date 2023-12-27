package org.launchcode.sprinklespre.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.sprinklespre.models.Course;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.data.CourseRepository;
import org.launchcode.sprinklespre.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses/index";
    }

    @GetMapping("/view/{courseId}")
    public String viewCourse(@PathVariable Integer courseId, Model model) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        return "courses/view";
    }

    @PostMapping("/enroll/{courseId}")
    public String enrollInACourse(@PathVariable Integer courseId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        if (user == null) {
            return "redirect:/login";
        }

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            model.addAttribute("enrollmentError", "Course was not found.");
            return "redirect:/courses";
        }

        Course course = optionalCourse.get();

        if (course.getUsers().contains(user)) {
            model.addAttribute("enrollmentError", "You are already enrolled in this course.");
            return "courses/view";
        }

        course.getUsers().add(user);
        courseRepository.save(course);

        user.getCourses().add(course);
        userRepository.save(user);

        return "courses/enroll";

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