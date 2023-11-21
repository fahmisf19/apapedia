package apap.tk.order.restservice;

import apap.tk.order.model.Order;
import apap.tk.order.model.OrderItem;
import apap.tk.order.repository.OrderDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<Order> getOrdersByCustomerId(UUID customerId) { return orderDb.findByCustomerId(customerId); }
    @Override
    public List<Order> getOrdersBySellerId(UUID sellerId) { return orderDb.findBySellerId(sellerId); }
    @Override
    public Map<Integer, Long> getSalesPerDayForCurrentMonth() {
        // Get the first day of the current month
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        // Get the last day of the current month
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date endDate = calendar.getTime();

        // Fetch orders within the date range
        List<Order> orders = orderDb.findByCreatedAtBetween(startDate, endDate);

        // Group orders by day of the month and count the number of orders per day
        return orders.stream()
                .collect(Collectors.groupingBy(order -> {
                    calendar.setTime(order.getCreatedAt());
                    return calendar.get(Calendar.DAY_OF_MONTH);
                }, Collectors.counting()));
    }
}
