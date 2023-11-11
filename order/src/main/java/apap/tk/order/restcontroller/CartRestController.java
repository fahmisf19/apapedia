package apap.tk.order.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apap.tk.order.dto.CartMapper;
import apap.tk.order.dto.request.CreateCartRequestDTO;
import apap.tk.order.model.Cart;
import apap.tk.order.restservice.CartRestService;

@RestController
@RequestMapping("/api")
public class CartRestController {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartRestService cartRestService;

    @PostMapping("/cart/create")
    public ResponseEntity<Cart> createCart(@RequestBody CreateCartRequestDTO cartDto) {
        var cart = cartMapper.createCartRequestDTOtoCart(cartDto);
        cartRestService.createRestCart(cart);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
}
