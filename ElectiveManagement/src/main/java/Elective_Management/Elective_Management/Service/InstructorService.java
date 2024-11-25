package Elective_Management.Elective_Management.Service;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Subject;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Exception.InstructorNotFoundException;
import Elective_Management.Elective_Management.Exception.SubjectNotFoundException;
import Elective_Management.Elective_Management.dao.InstructorDAO;
import Elective_Management.Elective_Management.dao.SubjectDAO;
import Elective_Management.Elective_Management.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {
    private InstructorDAO instructorDAO;
    private SubjectDAO subjectDAO;
    private UserDAO userDAO;

    @Autowired
    public InstructorService(InstructorDAO instructorDAO, SubjectDAO subjectDAO, UserDAO userDAO){
        this.instructorDAO = instructorDAO;
        this.subjectDAO = subjectDAO;
        this.userDAO = userDAO;
    }
    
    //method to save the instructor data
    public Instructor saveInstructor(Instructor instructor) {
        return this.instructorDAO.saveInstructor(instructor);
    }
    
    //method to get the instructor by id, throws exception if instructor not found
    public Instructor getInstructorById(int id) throws InstructorNotFoundException {
        Instructor instructor = this.instructorDAO.findInstructorById(id);
        if (instructor == null){
            throw new InstructorNotFoundException();
        }
        return instructor;
    }
    
    //method to get all instructors
    public List<Instructor> getAllInstructor() {
        return this.instructorDAO.findAllInstructors();
    }

    //method to delete instructor by id, subsequently also deletes the user data for the instructor
    public Instructor deleteInstructorById(int id) throws InstructorNotFoundException {
        Instructor instructor = this.instructorDAO.findInstructorById(id);
        if (instructor == null){
            throw new InstructorNotFoundException();
        }
        List<Subject> subjects = subjectDAO.listSubjectByInstructorId(id);
        for (Subject subject : subjects){
            subject.setInstructor(null);
            this.subjectDAO.updateSubject(subject);
        }
        User user = instructor.getUser();
        Instructor instructor1 = this.instructorDAO.deleteById(id);
        this.userDAO.delete(user);
        return instructor1;
    }

    //method to update instructor data, throws exception if instructor not found
    public Instructor updateInstructor(Instructor instructor) throws InstructorNotFoundException {
        Instructor inst = this.instructorDAO.findInstructorById(instructor.getId());
        if (inst == null){
            throw new InstructorNotFoundException();
        }
        return this.instructorDAO.updateInstructor(instructor);
    }

    //method to get the instructor for a given subject, throws exception if subject not found
    public Instructor getInstructorBySubjectId(Integer id) throws SubjectNotFoundException {
        Subject subject = this.subjectDAO.findSubjectById(id);
        if (subject == null){
            throw new SubjectNotFoundException();
        }
        return instructorDAO.getInstructorBySubjectId(id);
    }
    
    //method to get instructor data using USER ID
    public Instructor getByUserId(Integer id) {
        return instructorDAO.getInstructorByUserId(id);
    }
}