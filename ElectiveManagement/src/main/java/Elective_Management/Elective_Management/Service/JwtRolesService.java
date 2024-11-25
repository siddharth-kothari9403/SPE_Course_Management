package Elective_Management.Elective_Management.Service;

import Elective_Management.Elective_Management.Entity.Role;
import Elective_Management.Elective_Management.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtRolesService {
    @Autowired
    private RoleDAO roleDAO;

    //method to get role by name
    public Role findByName(String name) {
        return roleDAO.findRoleByName(name);
    }
}