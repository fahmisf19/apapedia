package apap.tk.order.restservice;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tk.order.model.Cart;
import apap.tk.order.model.CartItem;
import apap.tk.order.repository.CartDb;
import apap.tk.order.repository.CartItemDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartItemRestServiceImpl implements CartItemRestService{
    @Autowired
    private CartItemDb cartItemDb;

    @Autowired
    CartDb cartDb;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createRestCartItem(CartItem cartItem) {
        updateTotalPrice(cartItem);
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
    @Transactional
    public void updateRestCartItem(CartItem cartItem) {
        updateTotalPrice(cartItem);
        cartItemDb.save(cartItem);
    }    

    @Override
    public List<CartItem>  getCartItemByUserId(UUID userId) {
        return cartItemDb.findByCart_UserId(userId);
    }

    @Override
    @Transactional
    public void deteleRestCartItem(CartItem cartItem){
        updateTotalPriceDelete(cartItem);
        cartItemDb.deleteHard(cartItem.getId());
    };

    private void updateTotalPrice(CartItem cartItem) {
        var cart = cartItem.getCart();
        List<CartItem> cartItems = cart.getListCartItem();
        Integer totalPrice = 0;
        totalPrice += cartItem.getProductPrice() * cartItem.getQuantity();
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                if (item != cartItem) {
                    totalPrice += item.getProductPrice() * item.getQuantity();
                }
            }
        }
        cart.setTotalPrice(totalPrice);
        cartDb.save(cart);
        cartItem.setCart(cart);
    }

    private void updateTotalPriceDelete(CartItem cartItem) {
        var cart = cartItem.getCart();
        List<CartItem> cartItems = cart.getListCartItem();
        Integer totalPrice = 0;
    
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                if (item != cartItem) {
                    totalPrice += item.getProductPrice() * item.getQuantity();
                }
            }
        }
    
        cart.setTotalPrice(totalPrice);
        cartDb.save(cart);
    }
    
}