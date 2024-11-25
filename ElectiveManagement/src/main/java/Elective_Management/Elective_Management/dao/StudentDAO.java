package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.Student;

import java.util.List;

// DAO to access and update values from the student table in the database
public interface StudentDAO {
    public Student saveStudent(Student student);

    public Student findStudentById(Integer id);

    public Student deleteById(Integer id);

    public Student updateStudent(Student student);

    public List<Student> findAllstudents();

    public Student getStudentByUserId(Integer id);
}
