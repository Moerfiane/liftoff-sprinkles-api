package org.launchcode.sprinklespre.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.launchcode.sprinklespre.models.Module;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.data.ModuleRepository;
import org.launchcode.sprinklespre.models.dto.ChangePasswordDTO;
import org.launchcode.sprinklespre.models.dto.CompleteModuleDTO;
import org.launchcode.sprinklespre.models.dto.CourseProgressDTO;
import org.launchcode.sprinklespre.models.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.launchcode.sprinklespre.models.data.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:5173/dashboard", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.GET})
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModuleRepository moduleRepository;

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

    @PutMapping("/{userId}/password")
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.PUT, RequestMethod.OPTIONS})
    public ResponseEntity<?> changePassword(@PathVariable Integer userId, @RequestBody ChangePasswordDTO changePasswordDTO) {

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String currentPassword = changePasswordDTO.getCurrentPassword();
            String newPassword = changePasswordDTO.getNewPassword();
            System.out.println("current pw: " + currentPassword);
            System.out.println("new pw: " + newPassword);
            user.updatePassword(currentPassword, newPassword);
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("success", true, "message", "Password changed successfully"));
        } else {
            return ResponseEntity.ok(Map.of("success", false, "message", "User not found"));
        }
    }

}
