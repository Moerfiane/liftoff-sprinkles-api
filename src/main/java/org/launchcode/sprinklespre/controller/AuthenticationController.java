package org.launchcode.sprinklespre.controller;

//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;

import jakarta.servlet.http.HttpSession;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.data.UserRepository;
import org.launchcode.sprinklespre.models.dto.LoginFormDTO;
import org.launchcode.sprinklespre.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    //@Autowired
    //public  JavaMailSender mailSender;

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

        //String randomCode = generateRandomVerificationCode(); // Generate verification code
        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword());
        newUser.setRole(registerFormDTO.getRole());
        userRepository.save(newUser);



//        try {
//            sendVerificationEmail(newUser, "http://localhost:8080");
//        } catch (MessagingException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//            // Handle the exception, e.g., return an error response
//        }

        //setUserInSession(request.getSession(), newUser);
        return ResponseEntity.ok(Map.of("success", true, "message", "User registered successfully"));

    }

//    @GetMapping("/verify")
//    public String verifyUser(@RequestParam("code") String code) {
//        User user = userRepository.findByVerificationCode(code);
//        if (user == null || user.isEnabled()) {
//            return "Verification failed";
//        }
//        user.setVerificationCode(null);
//        user.setEnabled(true);
//        userRepository.save(user);
//        return "Account verified successfully";
//    }


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

        return ResponseEntity.ok(Map.of("success", true, "message", "User registered successfully", "userId", theUser.getId(), "role", theUser.getRole()));

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        //request.getSession().invalidate();
        return ResponseEntity.ok("Logout successful");
    }

}
