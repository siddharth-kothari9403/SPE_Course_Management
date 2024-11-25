package Elective_Management.Elective_Management.ServiceTest;


import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.StudentSubject;
import Elective_Management.Elective_Management.Entity.Student;
import Elective_Management.Elective_Management.Entity.Subject;
import Elective_Management.Elective_Management.Exception.*;
import Elective_Management.Elective_Management.Service.StudentSubjectService;
import Elective_Management.Elective_Management.dao.InstructorDAO;
import Elective_Management.Elective_Management.dao.StudentSubjectDAO;
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
public class StudentSubjectServiceTest {

    @Mock
    private StudentSubjectDAO studentSubjectDAO;

    @Mock
    private SubjectDAO subjectDAO;

    @Mock
    private InstructorDAO instructorDAO;

    @Mock
    private StudentDAO studentDAO;

    @Autowired
    @InjectMocks
    private StudentSubjectService studentSubjectService;

    private StudentSubject studentSubject;
    private List<StudentSubject> studentSubjectList;

    private Student student;
    private Subject subject;
    private Instructor instructor;

    @BeforeEach
    public void setUp()
    {
        this.studentSubject = new StudentSubject();
        this.studentSubject.setSlno(1);
        this.student = new Student();
        student.setId(1);
        this.subject = new Subject();
        subject.setSubjectCode(1);
        this.instructor = new Instructor();
        instructor.setId(1);
        this.subject.setInstructor(instructor);
        this.studentSubject.setStudent(student);
        this.studentSubject.setSubject(subject);
        this.studentSubject.setStartDate(new Timestamp(System.currentTimeMillis()));
        this.studentSubject.setEndDate(new Timestamp(System.currentTimeMillis()));
        this.studentSubjectList = new ArrayList<>();
    }

    @AfterEach
    public void tearDown()
    {
        this.studentSubject = null;
        this.studentSubjectList = null;
    }

    @Test
    public void givenStudentSubjectToAddShouldReturnAddedStudentSubject(){
        when(studentSubjectDAO.saveStudentSubject(any())).thenReturn(this.studentSubject);
        when(subjectDAO.findSubjectById(1)).thenReturn(this.subject);
        when(studentDAO.findStudentById(1)).thenReturn(this.student);
        StudentSubject studentSubject1 = this.studentSubjectService.saveStudentSubject(this.studentSubject);
        assertThat(studentSubject1).isEqualTo(studentSubject);
        verify(studentSubjectDAO,times(1)).saveStudentSubject(any());
    }

    @Test
    public void givenGetAllStudentSubjectsShouldReturnListOfAllStudentSubjects(){
        when(studentSubjectDAO.findAllStudentSubjects()).thenReturn(this.studentSubjectList);
        List<StudentSubject> studentSubjects = studentSubjectService.getAllStudentSubject();
        assertEquals(studentSubjects, studentSubjectList);
        verify(studentSubjectDAO, times(1)).findAllStudentSubjects();
    }

    @Test
    public void GivenIdWillReturnstudentSubject(){
        when(studentSubjectDAO.findStudentSubjectById(1)).thenReturn(this.studentSubject);

        StudentSubject newstudentSubject = this.studentSubjectService.getStudentSubjectById(1);
        assertThat(newstudentSubject).isEqualTo(this.studentSubject);
    }


    @Test
    public void GivenInstructorIdReturnStudentSubjects()
    {
        when(this.studentSubjectDAO.getByInstructorId(1)).thenReturn(this.studentSubjectList);
        when(this.instructorDAO.findInstructorById(1)).thenReturn(this.instructor);
        List<StudentSubject> studentSubjectList1 = this.studentSubjectService.getAllStudentSubjectbyInstructorId(1);
        assertThat(studentSubjectList1).isEqualTo(this.studentSubjectList);
        verify(this.studentSubjectDAO,times(1)).getByInstructorId(1);
    }

    @Test
    public void GivenStudentIdReturnStudentSubjects()
    {
        when(this.studentSubjectDAO.getByStudentId(1)).thenReturn(this.studentSubjectList);
        when(this.studentDAO.findStudentById(1)).thenReturn(this.student);
        List<StudentSubject> studentSubjectList1 = this.studentSubjectService.getAllStudentSubjectbyStudentId(1);
        assertThat(studentSubjectList1).isEqualTo(this.studentSubjectList);
        verify(this.studentSubjectDAO,times(1)).getByStudentId(1);
    }
    @Test
    public void GivenSubjectIdReturnStudentSubjects()
    {
        when(this.studentSubjectDAO.getBySubjectId(1)).thenReturn(this.studentSubjectList);
        when(this.subjectDAO.findSubjectById(1)).thenReturn(this.subject);
        List<StudentSubject> studentSubjectList1 = this.studentSubjectService.getAllStudentSubjectbySubjectId(1);
        assertThat(studentSubjectList1).isEqualTo(this.studentSubjectList);
        verify(this.studentSubjectDAO,times(1)).getBySubjectId(1);
    }
    @Test
    public void GivenIdWillDeletestudentSubject(){
        when(this.studentSubjectDAO.findStudentSubjectById(1)).thenReturn(this.studentSubject);
        when(this.studentSubjectDAO.deleteById(1)).thenReturn(this.studentSubject);
        StudentSubject studentSubject1 = this.studentSubjectService.deleteStudentSubjectById(1);
        assertThat(studentSubject1).isEqualTo(this.studentSubject);
        verify(this.studentSubjectDAO, times(1)).deleteById(1);

    }

    @Test
    public void GivenStudentSubjectorSubjectorStudentorInstructorNullWillThrowException(){
        when(this.studentSubjectDAO.findStudentSubjectById(1)).thenReturn(null);
        when(this.subjectDAO.findSubjectById(1)).thenReturn(null);
        when(this.instructorDAO.findInstructorById(1)).thenReturn(null);
        when(this.studentDAO.findStudentById(1)).thenReturn(null);
        assertThrows(StudentSubjectNotFoundException.class, () -> this.studentSubjectService.getStudentSubjectById(1));
        assertThrows(SubjectNotFoundException.class, () -> this.studentSubjectService.saveStudentSubject(this.studentSubject));
        assertThrows(SubjectNotFoundException.class,()->this.studentSubjectService.saveStudentSubject(this.studentSubject));
        assertThrows(InstructorNotFoundException.class,()->this.studentSubjectService.getAllStudentSubjectbyInstructorId(1));
        assertThrows(StudentNotFoundException.class,()->this.studentSubjectService.getAllStudentSubjectbyStudentId(1));
        assertThrows(SubjectNotFoundException.class,()->this.studentSubjectService.getAllStudentSubjectbySubjectId(1));
        assertThrows(StudentSubjectNotFoundException.class, () -> this.studentSubjectService.deleteStudentSubjectById(1));
        verify(this.studentSubjectDAO,never()).deleteById(1);
        verify(this.studentSubjectDAO,never()).saveStudentSubject(this.studentSubject);
        verify(this.studentSubjectDAO,times(2)).findStudentSubjectById(1);
        verify(this.studentSubjectDAO,never()).getByStudentId(1);
        verify(this.studentSubjectDAO,never()).getBySubjectId(1);
        verify(this.studentSubjectDAO,never()).getByInstructorId(1);
    }
}