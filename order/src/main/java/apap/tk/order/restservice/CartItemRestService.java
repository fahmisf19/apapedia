package apap.tk.order.restservice;

import java.util.List;
import java.util.UUID;

import apap.tk.order.model.CartItem;

public interface CartItemRestService {
    void createRestCartItem(CartItem cartItem);
    List<CartItem> getRestAllCartItem();
    CartItem getCartItemById(UUID Id);
    List<CartItem>  getCartItemByUserId(UUID userId);
    void deteleRestCartItem(CartItem cartItem);
}
