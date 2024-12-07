package Elective_Management.Elective_Management.Controller;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Subject;
import Elective_Management.Elective_Management.Service.InstructorService;
import Elective_Management.Elective_Management.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//controller for all the methods related to subjects
@RestController
@RequestMapping("/subject")
@CrossOrigin(origins = "http://192.168.49.2:30007")
public class SubjectController {

    private SubjectService subjectService;
    private InstructorService instructorService;
    @Autowired
    public SubjectController (SubjectService subjectService,InstructorService instructorService)
    {
        this.subjectService = subjectService;
        this.instructorService = instructorService;
    }

    //endpoint to get all subjects in the database
    @GetMapping("/allSubjects")
    public List<Subject> findAllCabs(){
        return this.subjectService.findAll();
    }

    //endpoint to get subject by the subject code
    @GetMapping("/{code}")
    public Subject findSubjectById(@PathVariable Integer code) {
        return this.subjectService.findById(code);
    }

    //endpoint to delete the subject by subject code
    @DeleteMapping("/delete/{code}")
    public Subject deleteSubjectById(@PathVariable Integer code){
        return this.subjectService.deleteSubjectById(code);
    }

    //endpoint to update subject details
    @PutMapping("/update")
    public Subject updateSubject(@RequestBody Subject subject)  {
        this.subjectService.updateSubject(subject);
        return subject;
    }

    //endpoint to get subjects by instructor id
    @GetMapping("/getByInstructorId/{id}")
    public List<Subject> getByInstructorId(@PathVariable Integer id){
        return this.subjectService.listByInstructorId(id);
    }

    //endpoint to add a subject
    @PostMapping("/save")
    public Subject addSubject(@RequestBody Subject subject){
        return this.subjectService.saveSubject(subject);
    }

    //endpoint to get the instructor for a given subject code
    @GetMapping("/getInstructor/{id}")
    public Instructor getInstructorbySubId(@PathVariable int id)
    {
        return this.instructorService.getInstructorBySubjectId(id);
    }

    //endpoint to update the instructor for a given subject
    @PutMapping("/assignInstructor/{id}")
    public Subject assignInstructor(@PathVariable int id, @RequestBody Instructor instructor)
    {
        return this.subjectService.assignInstructor(id,instructor);
    }

    //endpoint to remove the instructor for a given subject
    @PutMapping("/removeInstructor/{id}")
    public Subject removeInstructor(@PathVariable Integer id){
        return this.subjectService.removeInstructor(id);
    }
}