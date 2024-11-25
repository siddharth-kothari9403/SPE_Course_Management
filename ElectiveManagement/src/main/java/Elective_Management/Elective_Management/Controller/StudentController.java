package Elective_Management.Elective_Management.Controller;

import Elective_Management.Elective_Management.Entity.Student;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Service.JwtUserDetailsService;
import Elective_Management.Elective_Management.Service.StudentService;
import Elective_Management.Elective_Management.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentController {
    private StudentService studentService;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public StudentController(StudentService StudentService, JwtTokenUtil jwtTokenUtil,JwtUserDetailsService jwtUserDetailsService)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.studentService = StudentService;
    }

    //endpoint to get all the student records
    @GetMapping("/getAll")
    public List<Student> getAllStudent()
    {
        return this.studentService.getAllStudents();
    }

    //endpoint to get student record by using student id
    @GetMapping("/getbyID/{id}")
    public Student getStudentbyId(@PathVariable int id)
    {
        return this.studentService.getStudentById(id);
    }

    //endpoint to save a student, the user object is assigned using the token
    @PostMapping("/save")
    public Student saveStudent(@RequestBody Student Student, @RequestHeader String Authorization) {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        Student.setId(0);
        Student.setUser(user);
        return this.studentService.saveStudent(Student);
    }

    //endpoint to update the student records, the user object is extracted from the token, and then assigned
    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student Student, @RequestHeader String Authorization)
    {
        Student student = this.studentService.getStudentById(Student.getId());
        student.setStudentName(Student.getStudentName());
        student.setEmail(Student.getEmail());
        student.setPhone(Student.getPhone());
        return this.studentService.updateStudent(student);
    }

    //endpoint to delete the student
    @DeleteMapping("/delete/{id}")
    public Student deleteStudent(@PathVariable int id)
    {
        return this.studentService.deleteStudentById(id);
    }

    //endpoint to get the student details using user id, which is extracted from the token
    @GetMapping("/user/getStudent")
    public Student getByUserId(@RequestHeader String Authorization){
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        return studentService.getByUserId(user.getId());
    }
}
