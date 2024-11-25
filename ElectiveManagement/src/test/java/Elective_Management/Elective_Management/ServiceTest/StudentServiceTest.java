package Elective_Management.Elective_Management.ServiceTest;


import Elective_Management.Elective_Management.Entity.Student;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.Exception.StudentNotFoundException;
import Elective_Management.Elective_Management.Service.StudentService;
import Elective_Management.Elective_Management.dao.StudentDAO;
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
public class StudentServiceTest {
    @Mock
    private StudentDAO studentDAO;


    @Mock
    private  UserDAO userDAO;
    @Autowired
    @InjectMocks
    private StudentService studentService;



    private Student student;
    private List<Student> studentList;

    private User user;

    @BeforeEach
    public void setUp()
    {
        studentList = new ArrayList<>();
        this.student = new Student("test1","test1@gmail.com","99999999999");
        this.student.setId(1);
        this.user = new User();
        this.user.setId(1);
        this.student.setUser(user);
    }

    @AfterEach
    public void tearDown()
    {
        this.student = null;
        this.studentList = null;
        this.user = null;
    }

    @Test
    public void givenStudentToAddShouldReturnAddedStudent(){
        when(studentDAO.saveStudent(any())).thenReturn(this.student);
        Student student1 = this.studentService.saveStudent(this.student);
        assertThat(student1).isEqualTo(student);
        verify(studentDAO,times(1)).saveStudent(any());
    }

    @Test
    public void givenGetAllStudentsShouldReturnListOfAllStudents(){
        when(studentDAO.findAllstudents()).thenReturn(this.studentList);
        List<Student> students = studentService.getAllStudents();

        assertEquals(students, studentList);
        verify(studentDAO, times(1)).findAllstudents();
    }

    @Test
    public void GivenIdWillReturnstudent(){
        when(studentDAO.findStudentById(1)).thenReturn(this.student);

        Student newstudent = this.studentService.getStudentById(1);
        assertThat(newstudent).isEqualTo(this.student);
    }

    @Test
    public void GivenstudentWillUpdateIt() {
        when(studentDAO.findStudentById(1)).thenReturn(this.student);
        when(studentDAO.updateStudent(any())).thenReturn(this.student);

        Student newstudent = studentService.updateStudent(this.student);
        assertThat(newstudent).isEqualTo(this.student);
        verify(studentDAO, times(1)).updateStudent(this.student);
    }

    @Test
    public void GivenUserIdReturnStudent()
    {
        when(this.studentDAO.getStudentByUserId(1)).thenReturn(this.student);
        Student student1 = this.studentService.getByUserId(1);
        assertThat(student1).isEqualTo(student);
        verify(this.studentDAO,times(1)).getStudentByUserId(1);
    }

    @Test
    public void GivenIdWillDeletestudent(){
        when(this.studentDAO.findStudentById(1)).thenReturn(this.student);
        when(this.studentDAO.deleteById(1)).thenReturn(this.student);
        Student student1 = this.studentService.deleteStudentById(1);
        assertThat(student1).isEqualTo(this.student);
        verify(this.studentDAO, times(1)).deleteById(1);

    }

    @Test
    public void GivenStudentNullWillThrowException(){
        when(this.studentDAO.findStudentById(1)).thenReturn(null);

        assertThrows(StudentNotFoundException.class, () -> this.studentService.getStudentById(1));
        assertThrows(StudentNotFoundException.class, () -> this.studentService.updateStudent(this.student));
        assertThrows(StudentNotFoundException.class, () -> this.studentService.deleteStudentById(1));
        verify(this.studentDAO, never()).updateStudent(this.student);
        verify(this.studentDAO, never()).deleteById(1);
        verify(this.studentDAO, times(3)).findStudentById(1);
    }
    
}

