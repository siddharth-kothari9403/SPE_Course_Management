package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.Instructor;

import java.util.List;

// DAO to access and update values from the instructor table in the database
public interface InstructorDAO {

    public Instructor saveInstructor(Instructor Instructor);

    public Instructor findInstructorById(Integer id);

    public Instructor deleteById(Integer id);

    public Instructor updateInstructor(Instructor Instructor);

    public List<Instructor> findAllInstructors();

    public Instructor getInstructorBySubjectId(Integer id);

    public Instructor getInstructorByUserId(Integer id);
}
