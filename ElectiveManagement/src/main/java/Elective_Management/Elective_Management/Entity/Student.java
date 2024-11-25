package Elective_Management.Elective_Management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
// Student entity that will be used to map to student table.
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
//    student id.
    private Integer id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "student_name")
//   name of the student.
    private String studentName;

    @Column(name = "student_email")
//    email id of the student.
    private String email;

    @Column(name = "student_phno")
//    phone number of the student.
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
//    List of requests made by the student.
    private List<Request> requests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<StudentSubject> subjects;
//    List of subjects the student is part of.

    public Student(String studentName, String email, String phone) {
        this.studentName = studentName;
        this.email = email;
        this.phone = phone;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<StudentSubject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<StudentSubject> subjects) {
        this.subjects = subjects;
    }

    public Student (){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    fn to add request.
    public void addRequest(Request request){
        if (requests == null){
            requests = new ArrayList<Request>();
        }
        this.requests.add(request);
        request.setStudent(this);
    }
}
