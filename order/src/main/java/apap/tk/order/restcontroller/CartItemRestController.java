package apap.tk.order.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// import apap.tk.order.dto.CartItemMapper;
import apap.tk.order.dto.request.CreateCartItemRequestDTO;
import apap.tk.order.dto.request.UpdateCartItemRequestDTO;
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
    public ResponseEntity<CartItem> createCartItem(@RequestBody CreateCartItemRequestDTO cartItemDto) {
        try {
            var cartId = cartItemDto.getCartId();
            var cart = cartDb.findById(cartId).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cart not found with id: " + cartId));

            var cartItem = new CartItem();
            cartItem.setProductId(cartItemDto.getProductId());
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItem.setCart(cart);
            cartItem.setProductName(cartItemDto.getProductName());
            cartItem.setProductPrice(cartItemDto.getProductPrice());
            cartItemRestService.createRestCartItem(cartItem);
            return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cart not found with id: " + cartItemDto.getCartId());
        }
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<?> updateCartItemQuantity(@RequestBody UpdateCartItemRequestDTO updateDTO) {
        try {
            CartItem cartItem = cartItemRestService.getCartItemById(updateDTO.getCartItemId());
    
            if (cartItem == null) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cart item not found with id: " + updateDTO.getCartItemId()
                );
            }
    
            // Update quantity
            cartItem.setQuantity(updateDTO.getNewQuantity());
            cartItemRestService.updateRestCartItem(cartItem);
    
            return new ResponseEntity<>(cartItem, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            // If it's a known exception, rethrow it
            throw e;
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/cart-item/{userId}")
    public ResponseEntity<List<CartItem>> getCartItemByUserId(@PathVariable("userId") UUID userId) {
        List<CartItem> listCartItem = cartItemRestService.getCartItemByUserId(userId);
        if (listCartItem.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No items found for user with id: " + userId
            );
        }
        return new ResponseEntity<>(listCartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{idCartItem}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("idCartItem") UUID idCartItem){
        CartItem cartItem = cartItemRestService.getCartItemById(idCartItem);
        
        if (cartItem == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cart item not found with id: " + idCartItem
            );
        }
    
        cartItemRestService.deteleRestCartItem(cartItem);
        return new ResponseEntity<>("Cart item has been deleted", HttpStatus.OK);
    }      

}
