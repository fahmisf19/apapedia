package apap.tk.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.order.model.Cart;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CartDb extends JpaRepository<Cart, UUID> {
    
}
