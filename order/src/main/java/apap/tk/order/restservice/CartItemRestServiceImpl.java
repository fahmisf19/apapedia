package apap.tk.order.restservice;


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
}