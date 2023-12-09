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
import java.util.stream.IntStream;

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
    public Map<Integer, Long> getSalesPerDayForCurrentMonth(UUID sellerId) {
        // Get the first day of the current month
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        // Get the last day of the current month
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date endDate = calendar.getTime();

        // Fetch orders within the date range and for the specified sellerId
        List<Order> orders = orderDb.findByCreatedAtBetweenAndSellerId(startDate, endDate, sellerId);

        // Initialize a map with all days of the month and set the count to 0
        Map<Integer, Long> salesPerDay = IntStream.rangeClosed(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                .boxed()
                .collect(Collectors.toMap(day -> day, day -> 0L));

        // Update the counts based on the fetched orders
        salesPerDay.putAll(orders.stream()
                .collect(Collectors.groupingBy(order -> {
                    calendar.setTime(order.getCreatedAt());
                    return calendar.get(Calendar.DAY_OF_MONTH);
                }, Collectors.counting())));

        return salesPerDay;
    }

    // @Override
    // public void updateOrderStatus(UUID orderId, Integer newStatus) {
    //     Order order = getOrderRestById(orderId);
    //     if (order == null) {
    //         throw new ResponseStatusException(
    //                 HttpStatus.NOT_FOUND, "Order not found with id: " + orderId
    //         );
    //     }
    
    //     order.setStatus(newStatus);
    //     orderDb.save(order);
    // }
        
    @Override
    public void updateOrderStatus(UUID orderId, Integer newStatus) {
        Order order = getOrderRestById(orderId);
        if (order == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Order not found with id: " + orderId
            );
        }
    
        if (newStatus > 4) {
            // Jika status adalah 5, atur kembali ke 0
            order.setStatus(0);
        } else {
            // Jika status adalah 0 hingga 4, atur status seperti biasa
            order.setStatus(newStatus);
        }
    
        orderDb.save(order);
    }
    
}
