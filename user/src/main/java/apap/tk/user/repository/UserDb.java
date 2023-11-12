package main.java.apap.tk.user.repository;

import apap.tk.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserDb extends JpaRepository<User, UUID> {
    List<User> findByUserId(UUID userId);
}