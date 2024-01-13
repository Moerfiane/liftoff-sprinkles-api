package org.launchcode.sprinklespre.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.launchcode.sprinklespre.models.User;
import org.launchcode.sprinklespre.models.data.UserRepository;
import org.launchcode.sprinklespre.models.dto.LoginFormDTO;
import org.launchcode.sprinklespre.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    public  JavaMailSender mailSender;

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

        String randomCode = generateRandomVerificationCode(); // Generate verification code
        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword(), randomCode, false);
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

    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getUsername();
        String fromAddress = "your-email@example.com";
        String senderName = "Your Company Name";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername()); // Assuming getUsername() returns user's name
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
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

       return ResponseEntity.ok(Map.of("success", true, "message", "User registered successfully", "userId", theUser.getId(), "role", theUser.getRole()));
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        //request.getSession().invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    public static String generateRandomVerificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 6;
        Random random = new SecureRandom();
        StringBuilder verificationCode = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            verificationCode.append(characters.charAt(random.nextInt(characters.length())));
        }

        return verificationCode.toString();
    }
}
