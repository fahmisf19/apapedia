package apap.tk.order.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import apap.tk.order.model.CartItem;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CartItemDb extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCart_UserId(UUID userId);
    
    @Modifying
    @Query(value = "DELETE FROM CartItem c WHERE c.id = :id")
    void deleteHard(@Param("id") UUID id);
}
