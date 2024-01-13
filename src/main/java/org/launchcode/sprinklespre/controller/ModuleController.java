package org.launchcode.sprinklespre.controller;

import org.launchcode.sprinklespre.models.Course;
import org.launchcode.sprinklespre.models.Module;
import org.launchcode.sprinklespre.models.data.CourseRepository;
import org.launchcode.sprinklespre.models.data.ModuleRepository;
import org.launchcode.sprinklespre.models.dto.CourseFormDTO;
import org.launchcode.sprinklespre.models.dto.ModuleFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge=3600, methods={RequestMethod.POST})
@RequestMapping("/courses/modules")
public class ModuleController {
    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    CourseRepository courseRepository;

    //TODO: On front-end, need to navigate to "/courses/modules" to hook up this DTO
    @GetMapping("/create")
    public ResponseEntity<ModuleFormDTO> displayModuleForm(){
        ModuleFormDTO moduleFormDTO = new ModuleFormDTO();
        return ResponseEntity.ok(moduleFormDTO);
    }

    //TODO: Check that course/module relationship is updated
        //TODO: It's not! We need to addModules to course here
    //TODO: add category

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.POST} )
    public ResponseEntity<?> processAddCourseForm(@RequestBody ModuleFormDTO moduleFormDTO){
        System.out.println("moduleFormDTO.getName():" + moduleFormDTO.getName());
        Optional<Course> optCourse = courseRepository.findById(moduleFormDTO.getCourseId());
        if(optCourse.isPresent()) {
            Course aCourse = (Course) optCourse.get();
            Module newModule = new Module(
                    aCourse,
                    moduleFormDTO.getDescription(),
                    moduleFormDTO.getTools(),
                    moduleFormDTO.getIngredients(),
                    moduleFormDTO.getCategory(),
                    moduleFormDTO.getNotes(),
                    moduleFormDTO.getSteps()
            );
            newModule.setName(moduleFormDTO.getName());
            System.out.println("newModule.getName():" + newModule.getName());
            moduleRepository.save(newModule);
            return ResponseEntity.ok(Map.of("success", true, "message", "Module created"));
        } else {
            return ResponseEntity.ok(Map.of("success", false, "message", "Module not created; not associated with a course"));
        }

       }
}
