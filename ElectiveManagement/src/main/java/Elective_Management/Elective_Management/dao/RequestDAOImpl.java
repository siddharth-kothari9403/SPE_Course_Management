package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.Request;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RequestDAOImpl implements RequestDAO {
    private EntityManager entityManager;

    @Autowired
    public RequestDAOImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    // save a request for a subject
    @Override
    @Transactional
    public Request saveRequest(Request Request) {
        return this.entityManager.merge(Request);
    }

    // get a particular request by id
    @Override
    public Request findRequestById(Integer id) {
        return this.entityManager.find(Request.class,id);
    }

    // delete a request
    @Override
    @Transactional
    public Request deleteById(Integer id) {
        Request Request = this.entityManager.find(Request.class, id);
        this.entityManager.remove(Request);
        return Request;
    }

    // find all requests made across the institute
    @Override
    public List<Request> findAllRequests() {
        TypedQuery<Request> tq = this.entityManager.createQuery("From Request",Request.class);
        return tq.getResultList();
    }

    // find all pending requests made by a particular student
    @Override
    public List<Request> getRequestsByStudentId(Integer id){
        TypedQuery<Request> tpq = this.entityManager.createQuery("FROM Request where student.id = :id", Request.class);
        tpq.setParameter("id", id);
        return tpq.getResultList();
    }

    // get all pending requests for a subject by its code
    @Override
    public List<Request> getRequestBySubjectId(Integer id){
        TypedQuery<Request> tpq = this.entityManager.createQuery("FROM Request where subject.subjectCode = :id", Request.class);
        tpq.setParameter("id", id);
        return tpq.getResultList();
    }

    // get all requests for any subjects taught by a particular instructor
    @Override
    public List<Request> getRequestByInstructorId(Integer id){
        TypedQuery<Request> tpq = this.entityManager.createQuery("FROM Request where subject.instructor.Id = :id", Request.class);
        tpq.setParameter("id", id);
        return tpq.getResultList();
    }
}
