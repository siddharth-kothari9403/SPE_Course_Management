package Elective_Management.Elective_Management.ServiceTest;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Subject;
import Elective_Management.Elective_Management.Exception.InstructorNotFoundException;
import Elective_Management.Elective_Management.Exception.SubjectNotFoundException;
import Elective_Management.Elective_Management.Service.SubjectService;
import Elective_Management.Elective_Management.dao.InstructorDAO;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {
    @Mock
    private SubjectDAO subjectDAO;

    @Mock
    private InstructorDAO instructorDAO;

    @Mock
    private StudentDAO studentDAO;

    @Autowired
    @InjectMocks
    private SubjectService subjectService;

    private Subject subject;

    private List<Subject> subjectList;

    private Instructor instructor;

    @BeforeEach
    public void setUp()
    {
        this.subject = new Subject();
        this.subject.setSubjectName("Test1");
        this.subject.setSubjectCode(1);
        this.subjectList = new ArrayList<>();
        this.instructor = new Instructor();
        this.instructor.setId(1);
        this.subject.setInstructor(this.instructor);
    }

    @AfterEach
    public void tearDown()
    {
        this.subject = null;
        this.subjectList = null;
    }

    @Test
    public void givenSubjectToAddShouldReturnAddedSubject(){
        when(subjectDAO.saveSubject(any())).thenReturn(this.subject);
        Subject subject1 = this.subjectService.saveSubject(this.subject);
        assertThat(subject1).isEqualTo(subject);
        verify(subjectDAO,times(1)).saveSubject(any());
    }

    @Test
    public void givenGetAllSubjectsShouldReturnListOfAllSubjects(){
        when(subjectDAO.findAllSubjects()).thenReturn(this.subjectList);
        List<Subject> subjects = subjectService.findAll();

        assertEquals(subjects, subjectList);
        verify(subjectDAO, times(1)).findAllSubjects();
    }

    @Test
    public void GivenIdWillReturnsubject(){
        when(subjectDAO.findSubjectById(1)).thenReturn(this.subject);

        Subject newsubject = this.subjectService.findById(1);
        assertThat(newsubject).isEqualTo(this.subject);
    }

    @Test
    public void GivensubjectWillUpdateIt() {
        when(subjectDAO.findSubjectById(1)).thenReturn(this.subject);
        when(subjectDAO.updateSubject(any())).thenReturn(this.subject);

        Subject newsubject = subjectService.updateSubject(this.subject);
        assertThat(newsubject).isEqualTo(this.subject);
        verify(subjectDAO, times(1)).updateSubject(this.subject);
    }


    @Test
    public void GivenIdWillDeletesubject(){
        when(this.subjectDAO.findSubjectById(1)).thenReturn(this.subject);
        when(this.subjectDAO.deleteById(1)).thenReturn(this.subject);
        Subject subject1 = this.subjectService.deleteSubjectById(1);
        assertThat(subject1).isEqualTo(this.subject);
        verify(this.subjectDAO, times(1)).deleteById(1);
    }

    @Test
    public void getAllSubjectsbyInstructor()
    {
        when(this.subjectDAO.listSubjectByInstructorId(1)).thenReturn(this.subjectList);
        when(this.instructorDAO.findInstructorById(1)).thenReturn(this.instructor);
        List<Subject> subjectList1 = this.subjectService.listByInstructorId(1);
        assertThat(subjectList1).isEqualTo(this.subjectList);
        verify(this.subjectDAO,times(1)).listSubjectByInstructorId(1);
    }

    @Test
    public void GivenSubjectOrInstructorNullWillThrowException(){
        when(this.subjectDAO.findSubjectById(1)).thenReturn(null);
        when(this.instructorDAO.findInstructorById(1)).thenReturn(null);
        assertThrows(SubjectNotFoundException.class, () -> this.subjectService.findById(1));
        assertThrows(SubjectNotFoundException.class, () -> this.subjectService.updateSubject(this.subject));
        assertThrows(SubjectNotFoundException.class, () -> this.subjectService.deleteSubjectById(1));
        assertThrows(InstructorNotFoundException.class,() -> this.subjectService.listByInstructorId(1)) ;
        verify(this.subjectDAO, never()).updateSubject(this.subject);
        verify(this.subjectDAO, never()).deleteById(1);
        verify(this.subjectDAO, times(3)).findSubjectById(1);
        verify(this.subjectDAO, never()).listSubjectByInstructorId(1);
    }

    


}
