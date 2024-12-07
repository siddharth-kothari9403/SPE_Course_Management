package Elective_Management.Elective_Management.Controller;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Service.InstructorService;
import Elective_Management.Elective_Management.Service.JwtUserDetailsService;
import Elective_Management.Elective_Management.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@CrossOrigin(origins = "http://192.168.49.2:30007")
// contains and manages all the endpoints pertaining to Instructor.
public class InstructorController {

    private InstructorService instructorService;

    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public InstructorController(InstructorService InstructorService, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService)
    {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.instructorService = InstructorService;
    }

    @GetMapping("/getAll")
    // fn to get all instructors.
    public List<Instructor> getAllInstructor()
    {
        return this.instructorService.getAllInstructor();
    }

    @GetMapping("/getbyID/{id}")
    // fn to get instructor by id.
    public Instructor getInstructorbyId(@PathVariable int id)
    {
        return this.instructorService.getInstructorById(id);
    }

    @PostMapping("/save")
    // fn to save instructor.
    public Instructor saveInstructor(@RequestBody Instructor Instructor, @RequestHeader String Authorization)
    {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        Instructor.setId(0);
        Instructor.setUser(user);
        return this.instructorService.saveInstructor(Instructor);
    }

    @PutMapping("/update")
    // fn to update the instructor.
    public Instructor updateInstructor(@RequestBody Instructor Instructor, @RequestHeader String Authorization)
    {
        Instructor instructor = this.instructorService.getInstructorById(Instructor.getId());
        instructor.setInstructor_name(Instructor.getInstructor_name());
        instructor.setEmail(Instructor.getEmail());
        instructor.setPhone(Instructor.getPhone());
        return this.instructorService.updateInstructor(instructor);
    }

    @DeleteMapping("/delete/{id}")
    // fn to delete the instructor.
    public Instructor deleteInstructor(@PathVariable int id)
    {
        return this.instructorService.deleteInstructorById(id);
    }

    @GetMapping("/user/getInstructor")
    // fn to get instructor by passing user id.
    public Instructor getByUserId(@RequestHeader String Authorization){
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        return instructorService.getByUserId(user.getId());
    }
}
