package Elective_Management.Elective_Management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "instructor")
// instructor entity that will be mapped to instructor table.
public class Instructor {
    public Instructor() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_code")
//    instructor id.
    private int Id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "instructor_email")
//    email id of the instructor
    private String email;

    @Column(name = "instructor_phno")
//    phone number of the instructor.
    private String phone;

    @Column(name="instructor_name")
//    name of the instructor.
    private String instructor_name;

    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
//   List of subjects taught by the instructor.
    private List<Subject> subjects;

    public Instructor(String instructor_name, String email, String phone) {
//        this.user = user;
        this.instructor_name = instructor_name;
        this.email = email;
        this.phone = phone;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<Subject> courses) {
        this.subjects = courses;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
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
}
