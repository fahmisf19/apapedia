package apap.tk.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.users.model.Customer;

import java.util.UUID;

@Repository
public interface CustomerDb extends JpaRepository<Customer, UUID> {
}
