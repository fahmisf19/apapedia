package apap.tk.order.restcontroller;

import apap.tk.order.dto.OrderMapper;
import apap.tk.order.dto.request.CreateOrderRequestDTO;
import apap.tk.order.model.Order;
import apap.tk.order.model.OrderItem;
import apap.tk.order.restservice.OrderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderRestController {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRestService orderRestService;

    @PostMapping(value = "order/create")
    public ResponseEntity<Order> restCreateOrder(@RequestBody CreateOrderRequestDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            orderDTO.setCreatedAt(new Date());
            orderDTO.setUpdateAt(new Date());
            orderDTO.setStatus(0);
            orderDTO.setTotalPrice(orderRestService.calculateTotalPrice(orderDTO.getListOrderItem()));
            Order order = orderMapper.createOrderRequestDTOToOrder(orderDTO);
            orderRestService.createRestOrder(order);

            for (OrderItem orderItem : order.getListOrderItem()) {
                orderItem.setOrder(order);
            }

            orderRestService.createRestOrder(order);
            return ResponseEntity.ok().body(order);
        }
    }

    @PutMapping(value = "order/{idOrder}/update")
    public ResponseEntity<Order> restUpdateOrder(@PathVariable("idOrder") UUID idOrder,
                                @RequestParam("newStatus") Integer newStatus){
        var existingOrder = orderRestService.getOrderRestById(idOrder);
        if (existingOrder == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Order not found with id: " + idOrder
            );
        }

        existingOrder.setUpdateAt(new Date());
        existingOrder.setStatus(newStatus);
        orderRestService.updateRestOrder(existingOrder);
        return ResponseEntity.ok().body(existingOrder);
    }

    @GetMapping("order/getByCustomerId")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@RequestParam UUID customerId) {
        List<Order> orders = orderRestService.getOrdersByCustomerId(customerId);

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @GetMapping("order/getBySellerId")
    public ResponseEntity<List<Order>> getOrdersBySellerId(@RequestParam UUID sellerId) {
        List<Order> orders = orderRestService.getOrdersBySellerId(sellerId);

        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @RequestMapping(
            value = "order/quantity-per-day/{sellerId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.GET}
    )
    public ResponseEntity<Map<Integer, Long>> getQuantityPerDayForCurrentMonth(@PathVariable UUID sellerId) {
        Map<Integer, Long> quantityPerDay = orderRestService.getQuantityPerDayForCurrentMonth(sellerId);
        return new ResponseEntity<>(quantityPerDay, HttpStatus.OK);
    }
}
