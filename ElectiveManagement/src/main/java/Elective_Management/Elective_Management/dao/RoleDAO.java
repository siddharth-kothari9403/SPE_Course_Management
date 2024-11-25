package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// table stores the roles of users (admin/student/instructor etc)
@Repository
public interface RoleDAO extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}
