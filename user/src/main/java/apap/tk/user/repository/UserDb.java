package apap.tk.user.repository;

import apap.tk.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDb extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByUserId(UUID userId);
    Optional<UserEntity> findById(UUID id);
    UserEntity findByUsername(String username);
}