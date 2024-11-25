package Elective_Management.Elective_Management.Service;

import Elective_Management.Elective_Management.Entity.Student;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Exception.StudentNotFoundException;
import Elective_Management.Elective_Management.dao.StudentDAO;
import Elective_Management.Elective_Management.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private StudentDAO studentDAO;
    private UserDAO userDAO;

    @Autowired
    public StudentService(StudentDAO studentDAO, UserDAO userDAO){
        this.studentDAO = studentDAO;
        this.userDAO = userDAO;
    }

    //method to save student records to db
    public Student saveStudent(Student student){
        return this.studentDAO.saveStudent(student);
    }

    //method to update student records, throws exception if student not found
    public Student updateStudent(Student student) throws StudentNotFoundException{
        Student student1 = this.studentDAO.findStudentById(student.getId());
        if (student1 == null){
            throw new StudentNotFoundException();
        }
        return this.studentDAO.updateStudent(student);
    }

    //method to get student records by id, throws exception if student not found
    public Student getStudentById(Integer id) throws StudentNotFoundException {
        Student student = this.studentDAO.findStudentById(id);
        if (student == null){
            throw new StudentNotFoundException();
        }

        return student;
    }
    
    //method to delete student record by id, throws exception if student not found
    public Student deleteStudentById(Integer id) throws StudentNotFoundException {
        Student student = this.studentDAO.findStudentById(id);
        if (student == null){
            throw new StudentNotFoundException();
        }
        User user = student.getUser();
        this.studentDAO.deleteById(id);
        this.userDAO.delete(user);
        return student;
    }

    //method to get all students data
    public List<Student> getAllStudents(){
        return this.studentDAO.findAllstudents();
    }

    //method to get student data using USER ID
    public Student getByUserId(Integer id) {
        return studentDAO.getStudentByUserId(id);
    }
}
