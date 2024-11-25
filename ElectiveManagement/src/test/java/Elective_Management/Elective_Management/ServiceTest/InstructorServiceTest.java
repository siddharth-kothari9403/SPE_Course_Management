package Elective_Management.Elective_Management.ServiceTest;

import Elective_Management.Elective_Management.Entity.Instructor;
import Elective_Management.Elective_Management.Entity.Subject;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Exception.InstructorNotFoundException;
import Elective_Management.Elective_Management.Service.InstructorService;
import Elective_Management.Elective_Management.dao.InstructorDAO;
import Elective_Management.Elective_Management.dao.SubjectDAO;
import Elective_Management.Elective_Management.dao.UserDAO;
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

@ExtendWith(MockitoExtension.class)
public class InstructorServiceTest {
    @Mock
    private InstructorDAO instructorDAO;

    @Mock
    private SubjectDAO subjectDAO;

    @Mock
    private UserDAO userDAO;

    @Autowired
    @InjectMocks
    private InstructorService instructorService;

    private Instructor instructor;
    private List<Instructor> instructorList;

    private Subject subject;

    private User user;

    @BeforeEach
    public void setUp()
    {
        this.instructorList = new ArrayList<>();
        this.instructor = new Instructor();
        this.instructor.setId(1);
        this.instructor.setInstructor_name("Test1");
        this.subject = new Subject();
        this.subject.setInstructor(this.instructor);
        this.subject.setSubjectCode(1);
        this.subject.setSubjectName("SubjectTest");
        this.user = new User();
        this.user.setId(1);
        this.instructor.setUser(user);
    }


    @AfterEach
    public void tearDown()
    {
        this.instructor = null;
        this.instructorList = null;
        this.subject = null;
        this.user=null;
    }

    @Test
    public void givenInstructorToAddShouldReturnAddedInstructor(){
        when(this.instructorDAO.saveInstructor(any())).thenReturn(this.instructor);
        Instructor instructor1 = this.instructorService.saveInstructor(this.instructor);
        assertThat(instructor1).isEqualTo(this.instructor);
        verify(this.instructorDAO,times(1)).saveInstructor(any());

    }
    @Test
    public void givenGetAllInstructorsShouldReturnListOfAllInstructors(){
        when(this.instructorDAO.findAllInstructors()).thenReturn(this.instructorList);
        List<Instructor> instructorList1 = this.instructorService.getAllInstructor();
        assertEquals(instructorList1, this.instructorList);
        verify(this.instructorDAO, times(1)).findAllInstructors();
    }

    @Test
    public void GivenIdWillDeleteInstructor(){
        when(this.instructorDAO.findInstructorById(1)).thenReturn(this.instructor);
        when(this.instructorDAO.deleteById(1)).thenReturn(this.instructor);

        Instructor instructor1 = this.instructorService.deleteInstructorById(1);
        assertThat(instructor1).isEqualTo(this.instructor);
        verify(this.instructorDAO, times(1)).deleteById(1);
    }


    @Test
    public void GivenIdWillReturnInstructor(){
        when(this.instructorDAO.findInstructorById(1)).thenReturn(this.instructor);
        Instructor instructor1= this.instructorService.getInstructorById(1);
        assertThat(instructor1).isEqualTo(this.instructor);
    }

    @Test
    public void GivenInstructorWillUpdateIt() {
        when(this.instructorDAO.findInstructorById(1)).thenReturn(this.instructor);
        when(this.instructorDAO.updateInstructor(any())).thenReturn(this.instructor);

        Instructor instructor1 = this.instructorService.updateInstructor(this.instructor);
        assertThat(instructor1).isEqualTo(this.instructor);
        verify(this.instructorDAO, times(1)).updateInstructor(this.instructor);
    }

    @Test
    public void GivenInstructorNullWillThrowException(){
        when(this.instructorDAO.findInstructorById(1)).thenReturn(null);

        assertThrows(InstructorNotFoundException.class, () -> this.instructorService.getInstructorById(1));
        assertThrows(InstructorNotFoundException.class, () -> this.instructorService.updateInstructor(this.instructor));
        assertThrows(InstructorNotFoundException.class, () -> this.instructorService.deleteInstructorById(1));
        verify(this.instructorDAO, never()).updateInstructor(this.instructor);
        verify(this.instructorDAO, never()).deleteById(1);
        verify(this.instructorDAO, times(3)).findInstructorById(1);
    }

    @Test
    public void GivenSubjectIdReturnInstructor()
    {
        when(this.subjectDAO.findSubjectById(1)).thenReturn(this.subject);
        when(this.instructorDAO.getInstructorBySubjectId(1)).thenReturn(this.instructor);
        Instructor instructor1 = this.instructorService.getInstructorBySubjectId(1);
        assertEquals(instructor1,this.instructor);
        verify(this.instructorDAO,times(1)).getInstructorBySubjectId(1);
    }

    @Test
    public void GivenUserIdReturnInstructor()
    {
        when(this.instructorDAO.getInstructorByUserId(1)).thenReturn(this.instructor);
        Instructor instructor1 = this.instructorService.getByUserId(1);
        assertEquals(instructor1,this.instructor);
        verify(this.instructorDAO,times(1)).getInstructorByUserId(1);
    }
}