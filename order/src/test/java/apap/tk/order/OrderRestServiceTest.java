package apap.tk.order;

import apap.tk.order.model.Order;
import apap.tk.order.repository.OrderDb;
import apap.tk.order.restservice.OrderRestService;
import apap.tk.order.restservice.OrderRestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderRestServiceTest {
    @Mock
    private OrderDb orderDb;

    @InjectMocks
    OrderRestServiceImpl orderRestServiceImpl;

    @BeforeEach
    public void setUp() {
        // Create mock data for testing
        Order order1 = new Order();
        order1.setId(UUID.randomUUID());
        order1.setCreatedAt(new Date());
        order1.setUpdateAt(new Date());
        order1.setStatus(0);
        order1.setTotalPrice(100);
        order1.setCustomerId(UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a"));
        order1.setSellerId(UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e"));

        Order order2 = new Order();
        order2.setId(UUID.randomUUID());
        order2.setCreatedAt(new Date());
        order2.setUpdateAt(new Date());
        order2.setStatus(1);
        order2.setTotalPrice(200);
        order1.setCustomerId(UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a"));
        order1.setSellerId(UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e"));

        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(order1);
        mockOrders.add(order2);

        // Set up mock behavior
        Mockito.when(orderDb.findByCustomerId(UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a"))).thenReturn(mockOrders);
        Mockito.when(orderDb.findBySellerId(UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e"))).thenReturn(mockOrders);
    }

    @Test
    public void findOrderByCustomerId() {
        UUID customerId = UUID.fromString("86b01434-4894-4ec4-8f3e-f4d79da4412a");
        List<Order> orders = orderRestServiceImpl.getOrdersByCustomerId(customerId);
        assertEquals(2, orders.size());
    }

    @Test
    public void findOrderBySellerId() {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
        List<Order> orders = orderRestServiceImpl.getOrdersBySellerId(sellerId);
        assertEquals(2, orders.size());
    }
}
