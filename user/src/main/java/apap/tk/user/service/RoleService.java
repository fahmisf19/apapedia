package apap.tk.user.service;

import java.util.List;

import apap.tk.user.model.Role;

public interface RoleService {
    List<Role> getAllList();
    Role getRoleByRoleName(String role);
    Role addRole(Role role);
}
