package apap.tk.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.users.model.Seller;

import java.util.UUID;

@Repository
public interface SellerDb extends JpaRepository<Seller, UUID> {
}
