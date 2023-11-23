package apap.tk.order.restcontroller;

import apap.tk.order.dto.OrderMapper;
import apap.tk.order.dto.request.CreateOrderRequestDTO;
import apap.tk.order.dto.request.UpdateOrderRequestDTO;
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
                                 @RequestBody UpdateOrderRequestDTO orderDTO,
                                 BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            var existingOrder = orderRestService.getOrderRestById(idOrder);
            if (existingOrder == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found with id: " + idOrder
                );
            }
            orderDTO.setId(idOrder);
            orderDTO.setCreatedAt(existingOrder.getCreatedAt());
            orderDTO.setCustomerId(existingOrder.getCustomerId());
            orderDTO.setSellerId(existingOrder.getSellerId());
            orderDTO.setTotalPrice(existingOrder.getTotalPrice());

            // Set the updated listOrderItem from the DTO
            orderDTO.setListOrderItem(existingOrder.getListOrderItem());

            orderDTO.setUpdateAt(new Date());
            var order = orderMapper.updateOrderRequestDTOToOrder(orderDTO);
            orderRestService.updateRestOrder(order);
            return ResponseEntity.ok().body(order);
        }
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
            value = "order/sales-per-day",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = {RequestMethod.GET}
    )
    public ResponseEntity<Map<Integer, Long>> getSalesPerDayForCurrentMonth() {
        Map<Integer, Long> salesPerDay = orderRestService.getSalesPerDayForCurrentMonth();
        return new ResponseEntity<>(salesPerDay, HttpStatus.OK);
    }

}
