package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.StudentSubject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentSubjectDAOImpl implements StudentSubjectDAO{
    private EntityManager entityManager;

    @Autowired
    public StudentSubjectDAOImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
    
    // add a new student subject pairing
    @Override
    @Transactional
    public StudentSubject saveStudentSubject(StudentSubject StudentSubject) {
        return this.entityManager.merge(StudentSubject);
    }

    //find studentSubject pairing by id
    @Override
    public StudentSubject findStudentSubjectById(Integer id) {
        return this.entityManager.find(StudentSubject.class,id);
    }
    // delete a record by id
    @Override
    @Transactional
    public StudentSubject deleteById(Integer id) {
        StudentSubject StudentSubject = this.entityManager.find(StudentSubject.class, id);
        this.entityManager.remove(StudentSubject);
        return StudentSubject;
    }

    // all student subject pairing records
    @Override
    public List<StudentSubject> findAllStudentSubjects() {
        TypedQuery<StudentSubject> tq = this.entityManager.createQuery("From StudentSubject",StudentSubject.class);
        return tq.getResultList();
    }
    
    // get list of all courses taken by student
    @Override
    public List<StudentSubject> getByStudentId(Integer id){
        TypedQuery<StudentSubject> tpq = this.entityManager.createQuery("FROM StudentSubject where student.id = :id", StudentSubject.class);
        tpq.setParameter("id", id);
        return tpq.getResultList();
    }

    // get all students taking a subject
    @Override
    public List<StudentSubject> getBySubjectId(Integer id){
        TypedQuery<StudentSubject> tpq = this.entityManager.createQuery("FROM StudentSubject where subject.subjectCode = :id", StudentSubject.class);
        tpq.setParameter("id", id);
        return tpq.getResultList();
    }

    // get list all pairings where subject was taught by a particular instructor
    @Override
    public List<StudentSubject> getByInstructorId(Integer id){
        TypedQuery<StudentSubject> tpq = this.entityManager.createQuery("FROM StudentSubject where subject.instructor.Id = :id", StudentSubject.class);
        tpq.setParameter("id", id);
        return tpq.getResultList();
    }

    // get all times a particular student took a course taught by a specific instructor
    @Override
    public List<StudentSubject> getForStudentAndInstructor(Integer studentId, Integer instructorId) {
        TypedQuery<StudentSubject> tpq = this.entityManager.createQuery("FROM StudentSubject where subject.instructor.Id = :instructorId and student.id = :studentId", StudentSubject.class);
        tpq.setParameter("studentId", studentId);
        tpq.setParameter("instructorId", instructorId);
        return tpq.getResultList();
    }
}
