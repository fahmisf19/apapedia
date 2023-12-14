package apap.tk.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.users.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDb extends JpaRepository<User, UUID> {
    Optional<User> findByIdAndDeletedFalse(UUID id);
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
