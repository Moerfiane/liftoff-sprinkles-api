package org.launchcode.sprinklespre.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.dto.CourseProgressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationController authenticationController;

    @GetMapping("/enrolled-courses")
    public ResponseEntity<List<CourseProgressDTO>> getEnrolledCourses(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<CourseProgressDTO> enrolledCourses = user.getCourseProgressForUser(user);

        return ResponseEntity.ok(enrolledCourses);
    }
}
