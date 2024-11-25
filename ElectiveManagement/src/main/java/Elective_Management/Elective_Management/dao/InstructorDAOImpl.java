package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Subject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class InstructorDAOImpl implements InstructorDAO {

    private EntityManager entityManager;

    @Autowired
    public InstructorDAOImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    // save an instructor's info
    @Override
    @Transactional
    public Instructor saveInstructor(Instructor Instructor) {
        return this.entityManager.merge(Instructor);
    }

    // find instructor by instructor id
    @Override
    public Instructor findInstructorById(Integer id) {
        return this.entityManager.find(Instructor.class,id);
    }

    // delete instructor by id
    @Override
    @Transactional
    public Instructor deleteById(Integer id) {
        Instructor Instructor = this.entityManager.find(Instructor.class,id);
        this.entityManager.remove(Instructor);
        return Instructor;
    }

    // update records of instructor
    @Override
    @Transactional
    public Instructor updateInstructor(Instructor Instructor) {
        return this.entityManager.merge(Instructor);
    }

    // get list of all instructors of the institute
    @Override
    public List<Instructor> findAllInstructors() {
        TypedQuery<Instructor> tq = this.entityManager.createQuery("From Instructor",Instructor.class);
        return tq.getResultList();
    }

    // get info on instructor taking a particular subject / elective
    @Override
    public Instructor getInstructorBySubjectId(Integer id){
        Subject subject = this.entityManager.find(Subject.class, id);
        return subject.getInstructor();
    }

    // getting a instructor's personal information, extracted from jwt token
    // allows a instructor to update their own info
    @Override
    public Instructor getInstructorByUserId(Integer id) {
        TypedQuery<Instructor> query = this.entityManager.createQuery("FROM Instructor where user.id = :id", Instructor.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
