package apap.tk.order.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// import apap.tk.order.dto.CartItemMapper;
import apap.tk.order.dto.request.CreateCartItemRequestDTO;
import apap.tk.order.model.CartItem;
import apap.tk.order.repository.CartDb;
import apap.tk.order.restservice.CartItemRestService;

@RestController
@RequestMapping("/api")
public class CartItemRestController {
    // @Autowired
    // private CartItemMapper cartItemMapper;

    @Autowired
    private CartItemRestService cartItemRestService;

    @Autowired
    private CartDb cartDb;
    
    @PostMapping("/cart-item/create")
    public ResponseEntity<String> createCartItem(@RequestBody CreateCartItemRequestDTO cartItemDto) {
        var cartId = cartItemDto.getCartId();
        var cart = cartDb.findById(cartId).orElseThrow(() -> new ResponseStatusException
        (HttpStatus.NOT_FOUND, "Cart not found with id: " + cartId));

        var cartItem = new CartItem();
        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setCart(cart);
        cartItemRestService.createRestCartItem(cartItem);
        return ResponseEntity.ok("CartItem created successfully");
    }
}
