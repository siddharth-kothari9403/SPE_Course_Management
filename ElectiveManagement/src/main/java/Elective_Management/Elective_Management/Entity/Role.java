package Elective_Management.Elective_Management.Entity;

import jakarta.persistence.*;

@Entity
// Role Entity that will be mapped to the role table.
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "role_id")
//     role id.
    private long id;

    @Column(name = "role_name")
//    name of the role .
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
