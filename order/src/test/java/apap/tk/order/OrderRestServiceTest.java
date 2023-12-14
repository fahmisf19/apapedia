package apap.tk.order;

import apap.tk.order.model.Order;
import apap.tk.order.model.OrderItem;
import apap.tk.order.repository.OrderDb;
import apap.tk.order.restservice.OrderRestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderRestServiceTest {
    @Mock
    private OrderDb orderDb;

    @InjectMocks
    OrderRestServiceImpl orderRestServiceImpl;

    @BeforeEach
    void setUp() {
        UUID customerId = UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a");
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-02");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        // Create mock data for testing
        Order order1 = new Order();
        order1.setId(UUID.randomUUID());
        order1.setCreatedAt(date);
        order1.setUpdateAt(date);
        order1.setStatus(0);
        order1.setTotalPrice(0);
        order1.setCustomerId(customerId);
        order1.setSellerId(sellerId);

        Order order2 = new Order();
        order2.setId(UUID.randomUUID());
        order2.setCreatedAt(date);
        order2.setUpdateAt(date);
        order2.setStatus(1);
        order2.setTotalPrice(0);
        order2.setCustomerId(customerId);
        order2.setSellerId(sellerId);

        List<OrderItem> listOrderItem = new ArrayList<>();
        OrderItem orderItemMock = new OrderItem();
        orderItemMock.setId(UUID.randomUUID());
        orderItemMock.setQuantity(5);
        orderItemMock.setProductName("Sample Product");
        orderItemMock.setProductPrice(50);
        orderItemMock.setOrder(order1);
        listOrderItem.add(orderItemMock);
        order1.setListOrderItem(listOrderItem);

        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(order1);
        mockOrders.add(order2);

        // Set up mock behavior
        when(orderDb.findByCreatedAtBetweenAndSellerId(startDate, endDate, sellerId)).thenReturn(mockOrders);
        when(orderDb.findByCustomerId(UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a"))).thenReturn(mockOrders);
        when(orderDb.findBySellerId(UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e"))).thenReturn(mockOrders);
        when(orderDb.save(any(Order.class))).thenReturn(null);
        when(orderDb.findById(any(UUID.class))).thenAnswer(invocation -> {
            UUID id = invocation.getArgument(0);
            Optional<Order> order = mockOrders.stream().filter(o -> o.getId().equals(id)).findFirst();
            return order;
        });
    }

    @Test
    void findOrderByCustomerId() {
        UUID customerId = UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a");
        List<Order> orders = orderRestServiceImpl.getOrdersByCustomerId(customerId);
        assertEquals(2, orders.size());
    }

    @Test
    void findOrderBySellerId() {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
        List<Order> orders = orderRestServiceImpl.getOrdersBySellerId(sellerId);
        assertEquals(2, orders.size());
    }

    @Test
    void findOrderById() {
        Order mockOrder = new Order();
        UUID orderId = UUID.randomUUID();
        mockOrder.setId(orderId);
        mockOrder.setCreatedAt(new Date());
        mockOrder.setUpdateAt(new Date());
        mockOrder.setStatus(0);
        mockOrder.setTotalPrice(100);
        mockOrder.setCustomerId(UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a"));
        mockOrder.setSellerId(UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e"));

        when(orderDb.findById(any(UUID.class))).thenReturn(Optional.of(mockOrder));

        Order result = orderRestServiceImpl.getOrderRestById(orderId);

        assertEquals(orderId, result.getId());
        assertEquals(0, result.getStatus());
    }

    @Test
    void testCalculateTotalPrice() {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(3);
        orderItem1.setProductPrice(30);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setQuantity(2);
        orderItem2.setProductPrice(20);

        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        Integer totalPrice = orderRestServiceImpl.calculateTotalPrice(orderItems);

        assertEquals(3 * 30 + 2 * 20, totalPrice);
    }

    @Test
    void testGetQuantityPerDayForCurrentMonth() {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
        List<Order> orders = orderRestServiceImpl.getOrdersBySellerId(sellerId);

        var calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
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
        assertEquals(5, quantityPerDay.get(2));
        assertEquals(0, quantityPerDay.get(1));
    }

    @Test
    void testCreateRestOrder() {
        Order sampleOrder = new Order();
        orderRestServiceImpl.createRestOrder(sampleOrder);
        verify(orderDb, times(1)).save(sampleOrder);
        verifyNoMoreInteractions(orderDb);
    }

    @Test
    void testUpdateRestOrder() {
        // Create a sample Order object for testing
        Order sampleOrder = new Order();
        UUID orderId = UUID.randomUUID();
        sampleOrder.setId(orderId);
        sampleOrder.setCreatedAt(new Date());
        sampleOrder.setUpdateAt(new Date());
        sampleOrder.setStatus(5);
        sampleOrder.setTotalPrice(0);
        sampleOrder.setCustomerId(UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a"));
        sampleOrder.setSellerId(UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e"));

        // Mock the behavior of findById to return an Optional containing the sampleOrder
        when(orderDb.findById(any(UUID.class))).thenReturn(Optional.of(sampleOrder));

        // Call the method under test
        Order updatedOrder = orderRestServiceImpl.updateRestOrder(sampleOrder);

        // Verify that findById was called exactly once with the correct order ID
        verify(orderDb, times(1)).findById(orderId);

        // Verify that orderDb.save() was called exactly once with the correct Order object
        verify(orderDb, times(1)).save(sampleOrder);

        // Perform assertions on the updatedOrder or other conditions as needed
        assertEquals(5, updatedOrder.getStatus());
    }
}
