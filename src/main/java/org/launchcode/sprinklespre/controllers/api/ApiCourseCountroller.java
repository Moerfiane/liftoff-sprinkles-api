package org.launchcode.sprinklespre.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//TODO: Set up @CrossOrigin to localhost path for React
//TODO: @RestController
//TODO: @RequestMapping to appropriate path in React
public class ApiCourseCountroller {
    //TODO: Autowire CourseRepository
    //TODO: Return all courses: new ResponseEntity contain List<Course> courses of all courses
    //TODO: Return specific course: new ResponseEntity getCourseByID with @PathVariable
        //TODO: If coureOptional.isPresent() {return new ResponseEntity<> courseOptional.get(), HttpStatus.OK);
        //TODO: Else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //TODO: Assign appropriate GetMapping for each of the above methods (getAll, getById)
}
