package org.launchcode.sprinklespre.controller.api;


import org.launchcode.sprinklespre.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5182", maxAge = 3600)
@RestController
@RequestMapping("/api/login")
public class ApiController {
  @Autowired
  private UserRepository userRepository;


}
