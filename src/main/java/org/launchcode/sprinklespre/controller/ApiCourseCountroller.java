package org.launchcode.sprinklespre.controller;

import org.launchcode.sprinklespre.models.data.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:5174/", maxAge = 3600)
@RestController
@RequestMapping("/api/courses")
public class ApiCourseCountroller {
    @Autowired
    private CourseRepository courseRepository;

    //TODO: Return all courses: new ResponseEntity contain List<Course> courses of all courses
    //TODO: Return specific course: new ResponseEntity getCourseByID with @PathVariable
        //TODO: If coureOptional.isPresent() {return new ResponseEntity<> courseOptional.get(), HttpStatus.OK);
        //TODO: Else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //TODO: Assign appropriate GetMapping for each of the above methods (getAll, getById)
}
