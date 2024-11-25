package Elective_Management.Elective_Management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subjects")
// the subjects entity which will be mapped to subject table.
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_code")
//    the subject id.
    private int subjectCode;

    @Column(name = "subject_name")
//    the name of the subject.
    private String subjectName;

    @Column(name = "subject_desc")
//    the subject description.
    private String subjectDesc;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "instructor_code")
//    The instructor teaching the subject.
    private Instructor instructor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    @JsonIgnore
//    List of students enrolled in the subject.
    private List<StudentSubject> students;

    public Subject() {}

    public Subject(int sub_code,String sub_name,String sub_desc)
    {
        this.subjectCode = sub_code;
        this.subjectName = sub_name;
        this.subjectDesc = sub_desc;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<StudentSubject> getStudents() {
        return students;
    }

    public void setStudents(List<StudentSubject> students) {
        this.students = students;
    }
}