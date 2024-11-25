package Elective_Management.Elective_Management.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "request")
// request entity that will be used to map to request table.
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slno")
//     serial number for the request.
    private int slno;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
//   the student who is requesting.
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "subject_code")
//    Thw subjec that is being requested.
    private Subject subject;

    @Column(name = "start_date")
//    requeest start Date
    private Date startDate;

    @Column(name = "end_date")
//    request End Date.
    private Date endDate;

    public Request() {
    }

    public Request(Student student, Subject subject, Date startDate, Date endDate) {
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
