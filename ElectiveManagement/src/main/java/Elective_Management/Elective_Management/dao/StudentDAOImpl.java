package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO{

    private EntityManager entityManager;

    @Autowired
    public StudentDAOImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    // save a new student record
    @Override
    @Transactional
    public Student saveStudent(Student student) {
        return this.entityManager.merge(student);
    }

    // get a particular student by their id
    @Override
    public Student findStudentById(Integer id) {
        return this.entityManager.find(Student.class,id);
    }

    // delete a student
    @Override
    @Transactional
    public Student deleteById(Integer id) {
        Student student = this.entityManager.find(Student.class,id);
        this.entityManager.remove(student);
        return  student;
    }

    // update records of existing student
    @Override
    @Transactional
    public Student updateStudent(Student student) {
        return this.entityManager.merge(student);
    }

    // get all student records
    @Override
    public List<Student> findAllstudents() {
        TypedQuery<Student> tq = this.entityManager.createQuery("From Student",Student.class);
        return tq.getResultList();
    }

    // getting a student's personal information, extracted from jwt token
    // allows a student to update their own info
    @Override
    public Student getStudentByUserId(Integer id) {
        TypedQuery<Student> query = this.entityManager.createQuery("FROM Student where user.id = :id", Student.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
