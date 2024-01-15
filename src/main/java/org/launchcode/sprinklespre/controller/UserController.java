package org.launchcode.sprinklespre.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.dto.CourseProgressDTO;
import org.launchcode.sprinklespre.models.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.launchcode.sprinklespre.models.data.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:5173/dashboard", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.GET})
public class UserController {

    @Autowired UserRepository userRepository;
    @Autowired
    private AuthenticationController authenticationController;

    @GetMapping
    public ResponseEntity<UserDTO> getUserId(){
        System.out.println("Setting DTO");
        UserDTO userDTO = new UserDTO();
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    @CrossOrigin(origins = "*", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    public ResponseEntity<?> getEnrolledCourses(@RequestBody UserDTO userDTO, HttpServletRequest request, Model model) {
        System.out.println("Attempting to fetch enrolled courses");
        System.out.println(userDTO.getUserId());

        Optional<User> userOptional = userRepository.findById(userDTO.getUserId());

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            List<CourseProgressDTO> enrolledCourses = user.getCourseProgressForUser();
            return ResponseEntity.ok(Map.of("success", true, "data", enrolledCourses));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
