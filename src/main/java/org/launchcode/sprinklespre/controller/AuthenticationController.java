package org.launchcode.sprinklespre.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.data.UserRepository;
import org.launchcode.sprinklespre.models.dto.LoginFormDTO;
import org.launchcode.sprinklespre.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5182", maxAge = 3600)
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;
    private static final String userSessionKey = "user";



    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    @GetMapping("/register")
    public ResponseEntity<RegisterFormDTO> displayRegistrationForm() {
        RegisterFormDTO registerFormDTO = new RegisterFormDTO();
        return ResponseEntity.ok(registerFormDTO);
    }



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterFormDTO registerFormDTO) {


        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "User already exists"));
        }


        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            return ResponseEntity.ok(Map.of("success", false, "message", "Passwords do not match"));
        }
        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        //setUserInSession(request.getSession(), newUser);
        return ResponseEntity.ok(Map.of("success", true, "message", "User registered successfully"));

    }
    @GetMapping("/login")
    public ResponseEntity<LoginFormDTO> displayLoginForm() {
        LoginFormDTO loginFormDTO = new LoginFormDTO();
        return ResponseEntity.ok(loginFormDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginFormDTO loginFormDTO) {


        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

        if (theUser == null) {
            return ResponseEntity.badRequest().body("Invalid username");
        }

        String password = loginFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

       return ResponseEntity.ok("User logged in successfully");
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {

        return ResponseEntity.ok("Logout successful");
    }



}
