package Elective_Management.Elective_Management.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
// Studnet Subject entity that will be used to map to student subject table.
@Table(name = "student_subject")
public class StudentSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slno")
//    serial number of the student subject relation.
    private int slno;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
//    student inlvolved in the relation.
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "subject_code")
//    the subjects that are involved in the relation.
    private Subject subject;

    @Column(name = "start_date")
//    start Date of the course(subject).
    private Date startDate;

    @Column(name = "end_date")
//    end date of the cousr(subject).
    private Date endDate;

    public StudentSubject(){}

    public StudentSubject(Student student, Subject subject, Date startDate, Date endDate) {
        this.student = student;
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date start_date) {
        this.startDate = start_date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end_date) {
        this.endDate = end_date;
    }
}