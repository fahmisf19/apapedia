package apap.tk.order.restservice;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tk.order.model.CartItem;
import apap.tk.order.repository.CartItemDb;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartItemRestServiceImpl implements CartItemRestService{
    @Autowired
    private CartItemDb cartItemDb;

    @Override
    public void createRestCartItem(CartItem cartItem) {
        cartItemDb.save(cartItem);
    }

    @Override
    public List<CartItem> getRestAllCartItem(){
       return cartItemDb.findAll();
    }

    @Override
    public CartItem getCartItemById(UUID id) {
        return cartItemDb.findById(id).orElse(null);
    }

    @Override
    public List<CartItem>  getCartItemByUserId(UUID userId) {
        return cartItemDb.findByCart_UserId(userId);
    }

    @Override
    public void deteleRestCartItem(CartItem cartItem){
        cartItemDb.delete(cartItem);
    };
}