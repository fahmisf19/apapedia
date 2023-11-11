package apap.tk.order.restservice;

import apap.tk.order.model.Order;
import apap.tk.order.model.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderRestService {
    void createRestOrder(Order order);
    Order updateRestOrder(Order order);
    Order getOrderRestById(UUID id);
    Integer calculateTotalPrice(List<OrderItem> listOrderItem);
}
