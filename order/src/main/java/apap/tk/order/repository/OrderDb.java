package apap.tk.order.repository;

import apap.tk.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDb extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerId(UUID customerId);
    List<Order> findBySellerId(UUID sellerId);
    List<Order> findByCreatedAtBetween(Date startDate, Date endDate);
}
