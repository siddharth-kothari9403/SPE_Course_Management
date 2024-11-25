package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.Subject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SubjectDAOImpl implements SubjectDAO{
    private EntityManager entityManager;

    @Autowired
    public SubjectDAOImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    //save a new subject
    @Override
    @Transactional
    public Subject saveSubject(Subject Subject) {
        return this.entityManager.merge(Subject);
    }

    //find subject by subject code
    @Override
    public Subject findSubjectById(Integer id) {
        return this.entityManager.find(Subject.class,id);
    }

    //delete a subject by its subject code
    @Override
    @Transactional
    public Subject deleteById(Integer id) {
        Subject Subject = this.entityManager.find(Subject.class,id);
        this.entityManager.remove(Subject);
        return Subject;
    }

    //update existing subject record
    @Override
    @Transactional
    public Subject updateSubject(Subject Subject) {
        return this.entityManager.merge(Subject);
    }

    //get all subjects
    @Override
    public List<Subject> findAllSubjects() {
        TypedQuery<Subject> tq = this.entityManager.createQuery("From Subject",Subject.class);
        return tq.getResultList();
    }

    // get list of subjects taught by a particular instructor
    @Override
    public List<Subject> listSubjectByInstructorId(Integer id){
        TypedQuery<Subject> query = this.entityManager.createQuery("FROM Subject where instructor.id = :id", Subject.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
