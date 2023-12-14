package apap.tk.order.restservice;

import java.util.List;
import java.util.UUID;

import apap.tk.order.model.CartItem;

public interface CartItemRestService {
    void saveRestCartItem(CartItem cartItem);
    List<CartItem> getRestAllCartItem();
    CartItem getCartItemById(UUID id);
    List<CartItem>  getCartItemByUserId(UUID userId);
    void deteleRestCartItem(CartItem cartItem);
}
