package apap.tk.order.restservice;

import apap.tk.order.model.Order;
import apap.tk.order.model.OrderItem;
import apap.tk.order.repository.OrderDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderRestServiceImpl implements OrderRestService{
    @Autowired
    private OrderDb orderDb;

    @Override
    public void createRestOrder(Order order) { orderDb.save(order); }

    @Override
    public Order updateRestOrder(Order orderDTO) {
        Order order = getOrderRestById(orderDTO.getId());
        order.setCustomerId(orderDTO.getCustomerId());
        order.setSellerId(orderDTO.getSellerId());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setUpdateAt(orderDTO.getUpdateAt());
        order.setStatus(orderDTO.getStatus());
        order.setListOrderItem(orderDTO.getListOrderItem());
        orderDb.save(order);
        return order;
    }

    @Override
    public Order getOrderRestById(UUID id) {
        Optional<Order> optionalOrder = orderDb.findById(id);
        return optionalOrder.orElse(null);
    }

    @Override
    public Integer calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> orderItem.getProductPrice() * orderItem.getQuantity())
                .reduce(0, Integer::sum);
    }
}
