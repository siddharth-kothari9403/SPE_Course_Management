package Elective_Management.Elective_Management.Controller;


import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Request;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Service.*;
import Elective_Management.Elective_Management.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "http://192.168.58.2:30007")
// contains and manages all the endpoints pertaining to requests.
public class RequestController {

    private RequestService requestService;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;
    private InstructorService instructorService;
    private StudentService studentService;
    private SubjectService subjectService;

    @Autowired
    public RequestController(RequestService requestService, JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil,InstructorService instructorService,StudentService studentService,SubjectService subjectService) {
        this.requestService = requestService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.instructorService = instructorService;
        this.subjectService = subjectService;
        this.studentService = studentService;
    }

    @GetMapping("/getAll")
//     fn to get all requests.
    public List<Request> getAllRequest()
    {
        return this.requestService.getAllRequest();
    }

    @GetMapping("/getbyID/{id}")
//    fn to get request by id.
    public Request getRequestbyId(@PathVariable int id)
    {
        return this.requestService.getRequestbyId(id);
    }

    @PostMapping("/save")
//    fn to dave request.
    public Request saveRequest(@RequestBody Request request, @RequestHeader String Authorization) {
        request.setSlno(0);
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        Instructor instructor = instructorService.getInstructorBySubjectId(request.getSubject().getSubjectCode());
        request.getStudent().setUser(user);
        request.getStudent().addRequest(request);
        request.getSubject().setInstructor(instructor);
        return this.requestService.saveRequest(request);
    }

    @DeleteMapping("/delete/{id}")
//    fn to delete request by id.
    public Request deleteRequest(@PathVariable int id)
    {
        return this.requestService.deleteRequestbyId(id);
    }

    @GetMapping("/getbyStudent/{id}")
//    fn to get requests by student id.
    public List<Request> getRequestbyStudentId (@PathVariable int id)
    {
        return this.requestService.getRequestByStudentId(id);
    }

    @GetMapping("/getbyInstructorId/{id}")
//    fn to get instructor by instructor id.
    public List<Request> getRequestbyInstructorId(@PathVariable int id)
    {
        return this.requestService.getRequestsByInstructorId(id);
    }

    @GetMapping("/getbySubjectId/{id}")
//    fn to get subjects by subject id.
    public List<Request> getSubjectbyInstructorId(@PathVariable int id)
    {
        return this.requestService.getRequestsBySubjectId(id);
    }

}
