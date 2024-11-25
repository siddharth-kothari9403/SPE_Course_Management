package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.Subject;

import java.util.List;

// DAO to access and update values from the subject table in the database
public interface SubjectDAO {
    public Subject saveSubject(Subject Subject);

    public Subject findSubjectById(Integer id);

    public Subject deleteById(Integer id);

    public Subject updateSubject(Subject Subject);

    public List<Subject> findAllSubjects();

    public List<Subject> listSubjectByInstructorId(Integer id);
}
