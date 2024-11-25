package Elective_Management.Elective_Management.ServiceTest;


import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Request;
import Elective_Management.Elective_Management.Entity.Student;
import Elective_Management.Elective_Management.Entity.Subject;
import Elective_Management.Elective_Management.Exception.*;
import Elective_Management.Elective_Management.Service.RequestService;
import Elective_Management.Elective_Management.dao.InstructorDAO;
import Elective_Management.Elective_Management.dao.RequestDAO;
import Elective_Management.Elective_Management.dao.StudentDAO;
import Elective_Management.Elective_Management.dao.SubjectDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestDAO requestDAO;

    @Mock
    private SubjectDAO subjectDAO;

    @Mock
    private InstructorDAO instructorDAO;

    @Mock
    private StudentDAO studentDAO;

    @Autowired
    @InjectMocks
    private RequestService requestService;

    private Request request;
    private List<Request> requestList;
    private Student student;
    private Subject subject;
    private Instructor instructor;

    @BeforeEach
    public void setUp()
    {
        this.request = new Request();
        this.request.setSlno(1);
        this.student = new Student();
        this.student.setId(1);
        this.subject = new Subject();
        this.subject.setSubjectCode(1);
        this.instructor = new Instructor();
        this.instructor.setId(1);
        this.subject.setInstructor(instructor);
        this.request.setStudent(student);
        this.request.setSubject(subject);
        this.request.setStartDate(new Timestamp(System.currentTimeMillis()));
        this.request.setEndDate(new Timestamp(System.currentTimeMillis()));
        this.requestList = new ArrayList<>();
    }

    @AfterEach
    public void tearDown()
    {
        this.request = null;
        this.requestList = null;
    }

    @Test
    public void givenRequestToAddShouldReturnAddedRequest(){
        when(requestDAO.saveRequest(any())).thenReturn(this.request);
        when(subjectDAO.findSubjectById(1)).thenReturn(this.subject);
        when(studentDAO.findStudentById(1)).thenReturn(this.student);
        Request request1 = this.requestService.saveRequest(this.request);
        assertThat(request1).isEqualTo(request);
        verify(requestDAO,times(1)).saveRequest(any());
    }

    @Test
    public void givenGetAllRequestsShouldReturnListOfAllRequests(){
        when(requestDAO.findAllRequests()).thenReturn(this.requestList);
        List<Request> requests = requestService.getAllRequest();

        assertEquals(requests, requestList);
        verify(requestDAO, times(1)).findAllRequests();
    }

    @Test
    public void GivenIdWillReturnrequest(){
        when(requestDAO.findRequestById(1)).thenReturn(this.request);

        Request newrequest = this.requestService.getRequestbyId(1);
        assertThat(newrequest).isEqualTo(this.request);
    }


    @Test
    public void GivenInstructorIdReturnRequests()
    {
        when(this.requestDAO.getRequestByInstructorId(1)).thenReturn(this.requestList);
        when(this.instructorDAO.findInstructorById(1)).thenReturn(this.instructor);
        List<Request> requestList1 = this.requestService.getRequestsByInstructorId(1);
        assertThat(requestList1).isEqualTo(this.requestList);
        verify(this.requestDAO,times(1)).getRequestByInstructorId(1);
    }

    @Test
    public void GivenStudentIdReturnRequests()
    {
        when(this.requestDAO.getRequestsByStudentId(1)).thenReturn(this.requestList);
        when(this.studentDAO.findStudentById(1)).thenReturn(this.student);
        List<Request> requestList1 = this.requestService.getRequestByStudentId(1);
        assertThat(requestList1).isEqualTo(this.requestList);
        verify(this.requestDAO,times(1)).getRequestsByStudentId(1);
    }
    @Test
    public void GivenSubjectIdReturnRequests()
    {
        when(this.subjectDAO.findSubjectById(1)).thenReturn(this.subject);
        when(this.requestDAO.getRequestBySubjectId(1)).thenReturn(this.requestList);
        List<Request> requestList1 = this.requestService.getRequestsBySubjectId(1);
        assertThat(requestList1).isEqualTo(this.requestList);
        verify(this.requestDAO,times(1)).getRequestBySubjectId(1);
    }
    @Test
    public void GivenIdWillDeleterequest(){
        when(this.requestDAO.findRequestById(1)).thenReturn(this.request);
        when(this.requestDAO.deleteById(1)).thenReturn(this.request);
        Request request1 = this.requestService.deleteRequestbyId(1);
        assertThat(request1).isEqualTo(this.request);
        verify(this.requestDAO, times(1)).deleteById(1);
    }

    @Test
    public void GivenRequestorSubjectorStudentorInstructorNullWillThrowException(){
        when(this.requestDAO.findRequestById(1)).thenReturn(null);
        when(this.subjectDAO.findSubjectById(1)).thenReturn(null);
        when(this.instructorDAO.findInstructorById(1)).thenReturn(null);
        when(this.studentDAO.findStudentById(1)).thenReturn(null);
        assertThrows(RequestNotFoundException.class, () -> this.requestService.getRequestbyId(1));
        assertThrows(SubjectNotFoundException.class, () -> this.requestService.saveRequest(this.request));
        assertThrows(SubjectNotFoundException.class,()->this.requestService.saveRequest(this.request));
        assertThrows(InstructorNotFoundException.class,()->this.requestService.getRequestsByInstructorId(1));
        assertThrows(SubjectNotFoundException.class,()->this.requestService.getRequestsBySubjectId(1));
        assertThrows(StudentNotFoundException.class,()->this.requestService.getRequestByStudentId(1));
        assertThrows(RequestNotFoundException.class, () -> this.requestService.deleteRequestbyId(1));
        verify(this.requestDAO, never()).deleteById(1);
        verify(this.requestDAO,times(1)).saveRequest(this.request);
        verify(this.requestDAO, times(3)).findRequestById(1);
        verify(this.requestDAO,times(1)).getRequestBySubjectId(1);
        verify(this.requestDAO,times(1)).getRequestsByStudentId(1);
        verify(this.requestDAO,times(1)).getRequestByInstructorId(1);

    }

}
