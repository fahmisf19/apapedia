package apap.tk.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import apap.tk.user.model.Role;

public interface RoleDb extends JpaRepository<Role, Long>{
    List<Role> findAll();
    Optional<Role> findByRole(String role);
}
