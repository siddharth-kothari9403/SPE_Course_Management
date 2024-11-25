package Elective_Management.Elective_Management.Service;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Student;
import Elective_Management.Elective_Management.Entity.StudentSubject;
import Elective_Management.Elective_Management.Entity.Subject;
import Elective_Management.Elective_Management.Exception.InstructorNotFoundException;
import Elective_Management.Elective_Management.Exception.StudentNotFoundException;
import Elective_Management.Elective_Management.Exception.StudentSubjectNotFoundException;
import Elective_Management.Elective_Management.Exception.SubjectNotFoundException;
import Elective_Management.Elective_Management.dao.*;
import Elective_Management.Elective_Management.dao.StudentSubjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSubjectService {

    private StudentSubjectDAO studentSubjectDAO;
    private StudentDAO studentDAO;

    private SubjectDAO subjectDAO;

    private InstructorDAO instructorDAO;

    @Autowired
    public StudentSubjectService (StudentSubjectDAO StudentSubjectDAO, StudentDAO studentDAO,SubjectDAO subjectDAO,InstructorDAO instructorDAO) {
        this.studentSubjectDAO = StudentSubjectDAO;
        this.studentDAO = studentDAO;
        this.subjectDAO = subjectDAO;
        this.instructorDAO = instructorDAO;
    }

    //method to save a student subject object to the db, throws exception if student or subject is not found
    public StudentSubject saveStudentSubject(StudentSubject StudentSubject) throws StudentNotFoundException,SubjectNotFoundException {
        int sid = StudentSubject.getStudent().getId();
        int subid = StudentSubject.getSubject().getSubjectCode();
        Subject subject = this.subjectDAO.findSubjectById(subid);
        Student student = this.studentDAO.findStudentById(sid);
        if(subject == null)
        {
            throw new SubjectNotFoundException();
        }
        if(student==null)
        {
            throw new StudentNotFoundException();
        }
        return this.studentSubjectDAO.saveStudentSubject(StudentSubject);
    }

    //method to get student subject by id, throws exception if not found
    public StudentSubject getStudentSubjectById(int id) throws StudentSubjectNotFoundException {
        StudentSubject ss = this.studentSubjectDAO.findStudentSubjectById(id);
        if (ss == null){
            throw new StudentSubjectNotFoundException();
        }
        return this.studentSubjectDAO.findStudentSubjectById(id);
    }

    //method to get all student subject records
    public List<StudentSubject> getAllStudentSubject()
    {
        return this.studentSubjectDAO.findAllStudentSubjects();
    }

    //method to delete a student subject object by id, throws exception if not found
    public StudentSubject deleteStudentSubjectById(int id) throws StudentSubjectNotFoundException {
        StudentSubject ss = this.studentSubjectDAO.findStudentSubjectById(id);
        if (ss == null){
            throw new StudentSubjectNotFoundException();
        }
        this.studentSubjectDAO.deleteById(id);
        return ss;
    }

    //method to get all records by sibject id, throws exception if subject not found
    public List<StudentSubject> getAllStudentSubjectbySubjectId(Integer id) throws SubjectNotFoundException
    {
        Subject subject = this.subjectDAO.findSubjectById(id);
        if(subject==null)
        {
            throw new SubjectNotFoundException();
        }
        return this.studentSubjectDAO.getBySubjectId(id);
    }

    //method to list all records by student id, throws exception if student not found
    public List<StudentSubject> getAllStudentSubjectbyStudentId(Integer id) throws StudentNotFoundException
    {
        Student student = this.studentDAO.findStudentById(id);
        if(student==null)
        {
            throw new StudentNotFoundException();
        }
        return this.studentSubjectDAO.getByStudentId(id);
    }

    //method to get all records by instructor id, throws exception if instructor not found
    public List<StudentSubject> getAllStudentSubjectbyInstructorId(Integer id) throws InstructorNotFoundException
    {
        Instructor instructor = this.instructorDAO.findInstructorById(id);
        if(instructor==null)
        {
            throw new InstructorNotFoundException();
        }
        return this.studentSubjectDAO.getByInstructorId(id);
    }

    //method to get records for a particular student and instructor combo
    public List<StudentSubject> getForStudentAndInstructor(Integer studentId, Integer instructorId) throws StudentSubjectNotFoundException, InstructorNotFoundException{
        Instructor instructor = this.instructorDAO.findInstructorById(instructorId);
        if (instructor == null){
            throw new InstructorNotFoundException();
        }
        Student student = this.studentDAO.findStudentById(studentId);
        if (student == null){
            throw new StudentSubjectNotFoundException();
        }
        return this.studentSubjectDAO.getForStudentAndInstructor(studentId, instructorId);
    }
}
