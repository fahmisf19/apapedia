package apap.tk.order.dto;

import apap.tk.order.dto.request.CreateOrderRequestDTO;
import apap.tk.order.dto.request.UpdateOrderRequestDTO;
import apap.tk.order.model.Order;
import apap.tk.order.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "listOrderItem", source = "listOrderItem")
    Order createOrderRequestDTOToOrder(CreateOrderRequestDTO createOrderRequestDTO);

    Order updateOrderRequestDTOToOrder(UpdateOrderRequestDTO updateOrderRequestDTO);

    UpdateOrderRequestDTO orderToUpdateOrderRequestDTO(Order Order);
}
