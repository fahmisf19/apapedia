package apap.tk.order.dto;

import org.mapstruct.Mapper;

import apap.tk.order.dto.request.CreateCartRequestDTO;
import apap.tk.order.model.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart createCartRequestDTOtoCart(CreateCartRequestDTO createCartRequestDTO);
}
