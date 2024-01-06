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

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:5173", maxAge = 3600, methods = {RequestMethod.POST} )
    public ResponseEntity<?> processAddCourseForm(@RequestBody ModuleFormDTO moduleFormDTO){
        System.out.println(moduleFormDTO.toString());
        Module newModule = new Module();
        return ResponseEntity.ok(Map.of("success", true, "message", "Module created"));
    }
}
