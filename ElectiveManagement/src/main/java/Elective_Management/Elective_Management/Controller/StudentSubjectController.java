package Elective_Management.Elective_Management.Controller;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Request;
import Elective_Management.Elective_Management.Entity.StudentSubject;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Service.InstructorService;
import Elective_Management.Elective_Management.Service.JwtUserDetailsService;
import Elective_Management.Elective_Management.Service.RequestService;
import Elective_Management.Elective_Management.Service.StudentSubjectService;
import Elective_Management.Elective_Management.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentSubject")
@CrossOrigin(origins = "http://192.168.49.2:30007")
// contains and managees all the endpoints pertaining to studnesubject.
public class StudentSubjectController {
    private StudentSubjectService studentSubjectService;
    private InstructorService instructorService;
    private JwtUserDetailsService jwtUserDetailsService;
    private RequestService requestService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public StudentSubjectController(StudentSubjectService StudentSubjectService, InstructorService instructorService, JwtUserDetailsService jwtUserDetailsService, RequestService requestService, JwtTokenUtil jwtTokenUtil)
    {
        this.studentSubjectService = StudentSubjectService;
        this.instructorService = instructorService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.requestService = requestService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/getAll")
//    get all student subjects.
    public List<StudentSubject> getAllStudentSubject()
    {
        return this.studentSubjectService.getAllStudentSubject();
    }

    @GetMapping("/getbyID/{id}")
//    get student subject by id.
    public StudentSubject getStudentSubjectbyId(@PathVariable int id)
    {
        return this.studentSubjectService.getStudentSubjectById(id);
    }

    @PostMapping("/save")
//    save student subject.
    public StudentSubject saveStudentSubject(@RequestBody StudentSubject StudentSubject)
    {
        return this.studentSubjectService.saveStudentSubject(StudentSubject);
    }


    @DeleteMapping("/delete/{id}")
//    delete student subject by id.
    public StudentSubject deleteStudentSubject(@PathVariable int id)
    {
        return this.studentSubjectService.deleteStudentSubjectById(id);
    }

    @GetMapping("/getByInstructor/{id}")
//    get list of student subjects by instructor id.
    public List<StudentSubject> getbyInstructorId(@PathVariable int id)
    {
        return this.studentSubjectService.getAllStudentSubjectbyInstructorId(id);
    }

    @GetMapping("/getByStudent/{id}")
//    get list of student subject by student id.
    public List<StudentSubject> getbyStudentId(@PathVariable int id)
    {
        return this.studentSubjectService.getAllStudentSubjectbyStudentId(id);
    }

    @PostMapping("/accept")
    @Transactional
//    accpet a request from the student and save it as a Student Subject.
    public StudentSubject accept(@RequestBody Request request){
        StudentSubject ss = new StudentSubject();
        ss.setEndDate(request.getEndDate());
        ss.setStartDate(request.getStartDate());
        ss.setStudent(request.getStudent());
        ss.setSubject(request.getSubject());
        Instructor instructor = instructorService.getInstructorBySubjectId(request.getSubject().getSubjectCode());
        ss.getSubject().setInstructor(instructor);
        User user = jwtUserDetailsService.getUserByUsername(request.getStudent().getUser().getUsername());
        ss.getStudent().setUser(user);

        StudentSubject ss1 = saveStudentSubject(ss);
        requestService.deleteRequestbyId(request.getSlno());
        return ss1;
    }

    //endpoint to get records by subject code
    @GetMapping("/getBySubject/{id}")
    public List<StudentSubject> getbySubjectId(@PathVariable int id)
    {
        return this.studentSubjectService.getAllStudentSubjectbySubjectId(id);
    }

    //endpoint to get student subject records for a given student instructor combo
    @GetMapping("/getForStudentAndInstructor/{id}")
    public List<StudentSubject> getForStudentAndInstructor(@PathVariable int id, @RequestHeader String Authorization){
        User user = jwtUserDetailsService.getUserByUsername(jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        Instructor instructor = instructorService.getByUserId(user.getId());
        return this.studentSubjectService.getForStudentAndInstructor(id, instructor.getId());
    }


}
