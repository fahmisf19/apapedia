package apap.tk.order.restservice;

import apap.tk.order.model.Order;
import apap.tk.order.model.OrderItem;
import apap.tk.order.repository.OrderDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        var order = getOrderRestById(orderDTO.getId());
        order.setCustomerId(orderDTO.getCustomerId());
        order.setSellerId(orderDTO.getSellerId());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setUpdateAt(orderDTO.getUpdateAt());
        order.setStatus(orderDTO.getStatus());
        order.setListOrderItem(orderDTO.getListOrderItem());
        if (orderDTO.getStatus() > 5) {
            // Jika status adalah 5, atur kembali ke 0
            order.setStatus(0);
        } else {
            // Jika status adalah 0 hingga 4, atur status seperti biasa
            order.setStatus(orderDTO.getStatus());
        }
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
    public Map<Integer, Long> getQuantityPerDayForCurrentMonth(UUID sellerId) {
        // Get the first day of the current month
        var calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        var startDate = calendar.getTime();

        // Get the last day of the current month
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        var endDate = calendar.getTime();

        // Fetch orders within the date range and for the specified sellerId
        List<Order> orders = orderDb.findByCreatedAtBetweenAndSellerId(startDate, endDate, sellerId);

        // Initialize a map with all days of the month and set the quantity to 0
        Map<Integer, Long> quantityPerDay = IntStream.rangeClosed(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                .boxed()
                .collect(Collectors.toMap(day -> day, day -> 0L));

        // Update the quantities based on the fetched orders
        for (Order order : orders) {
            for (OrderItem orderItem : order.getListOrderItem()) {
                calendar.setTime(order.getCreatedAt());
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                quantityPerDay.merge(dayOfMonth, Long.valueOf(orderItem.getQuantity()), Long::sum);
            }
        }

        return quantityPerDay;
    }
    
}
