package Elective_Management.Elective_Management.Service;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Request;
import Elective_Management.Elective_Management.Entity.Student;
import Elective_Management.Elective_Management.Entity.Subject;
import Elective_Management.Elective_Management.Exception.InstructorNotFoundException;
import Elective_Management.Elective_Management.Exception.RequestNotFoundException;
import Elective_Management.Elective_Management.Exception.StudentNotFoundException;
import Elective_Management.Elective_Management.Exception.SubjectNotFoundException;
import Elective_Management.Elective_Management.dao.InstructorDAO;
import Elective_Management.Elective_Management.dao.RequestDAO;
import Elective_Management.Elective_Management.dao.StudentDAO;
import Elective_Management.Elective_Management.dao.SubjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RequestService {
    private RequestDAO RequestDAO;

    private StudentDAO studentDAO;

    private SubjectDAO subjectDAO;

    private InstructorDAO instructorDAO;

    @Autowired
    public RequestService (RequestDAO RequestDAO,StudentDAO studentDAO,SubjectDAO subjectDAO,InstructorDAO instructorDAO)
    {
        this.RequestDAO = RequestDAO;
        this.studentDAO = studentDAO;
        this.subjectDAO = subjectDAO;
        this.instructorDAO = instructorDAO;
    }

    //method to save a request, throws exception if subject or student not found
    public Request saveRequest(Request Request) throws SubjectNotFoundException, StudentNotFoundException {
        int sid = Request.getStudent().getId();
        int subid = Request.getSubject().getSubjectCode();
        Subject subject = this.subjectDAO.findSubjectById(subid);
        Student student = this.studentDAO.findStudentById(sid);
        if(student==null)
        {
            throw new StudentNotFoundException();
        }
        if(subject==null)
        {
            throw new SubjectNotFoundException();
        }
        return this.RequestDAO.saveRequest(Request);
    }

    //method to get request by id, throws exception if request not found
    public Request getRequestbyId(int id) throws RequestNotFoundException {
        Request request = this.RequestDAO.findRequestById(id);
        if (request == null){
            throw new RequestNotFoundException();
        }
        return request;
    }

    //method to get all requests in the db
    public List<Request> getAllRequest() {
        return this.RequestDAO.findAllRequests();
    }

    //method to delete a request by id, throws exception if request not found
    public Request deleteRequestbyId(int id) throws RequestNotFoundException{
        Request request = this.RequestDAO.findRequestById(id);
        if (request == null){
            throw new RequestNotFoundException();
        }
        this.RequestDAO.deleteById(id);
        return request;
    }

    //method to get the requests for a given instructor, throws exception if instructor not found
    public List<Request> getRequestsByInstructorId(Integer id) throws InstructorNotFoundException {
        Instructor instructor = this.instructorDAO.findInstructorById(id);
        if(instructor==null)
        {
            throw new InstructorNotFoundException();
        }
        return this.RequestDAO.getRequestByInstructorId(id);
    }

    //method to get requests by subject id, throws exception if subject not found
    public List<Request> getRequestsBySubjectId(Integer id) throws SubjectNotFoundException{
        Subject subject = this.subjectDAO.findSubjectById(id);
        if(subject==null)
        {
            throw new SubjectNotFoundException();
        }
        return this.RequestDAO.getRequestBySubjectId(id);
    }

    //method to get requests for a given student, throws error if student not found
    public List<Request> getRequestByStudentId(Integer id) throws StudentNotFoundException{
        Student student = this.studentDAO.findStudentById(id);
        if(student==null)
        {
            throw new StudentNotFoundException();
        }
        return this.RequestDAO.getRequestsByStudentId(id);
    }

}
