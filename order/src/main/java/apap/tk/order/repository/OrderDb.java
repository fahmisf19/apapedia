package apap.tk.order.repository;

import apap.tk.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderDb extends JpaRepository<Order, UUID> {

}
