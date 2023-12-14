package apap.tk.order.dto;

import org.mapstruct.Mapper;

import apap.tk.order.dto.request.CreateCartItemRequestDTO;
import apap.tk.order.dto.request.UpdateCartItemRequestDTO;
import apap.tk.order.model.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem createCartItemRequestDTOtoCartItem(UpdateCartItemRequestDTO updateDTO);
}
