package apap.tk.order.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tk.order.model.Cart;
import apap.tk.order.repository.CartDb;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartRestServiceImpl implements CartRestService {
    @Autowired
    private CartDb cartDb;

    @Override
    public void createRestCart(Cart cart) {
        cartDb.save(cart);
    }

}
