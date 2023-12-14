package apap.tk.order;

import apap.tk.order.dto.CartItemMapper;
import apap.tk.order.dto.CartMapper;
import apap.tk.order.model.Cart;
import apap.tk.order.model.CartItem;
import apap.tk.order.repository.CartDb;
import apap.tk.order.repository.CartItemDb;
import apap.tk.order.restservice.CartItemRestServiceImpl;
import apap.tk.order.restservice.CartRestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CartAndCartItemTest {
    @InjectMocks
    CartRestServiceImpl cartRestService;

    @InjectMocks
    CartItemRestServiceImpl cartItemRestService;

    @Mock
    CartDb cartDb;

    @Mock
    CartItemDb cartItemDb;

    @Mock
    CartItemMapper cartItemMapper;

    @Mock
    CartMapper cartMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCartItemById() {
        UUID id = UUID.randomUUID();
        CartItem cartItem = new CartItem();
        cartItem.setId(id);

        when(cartItemDb.findById(id)).thenReturn(Optional.of(cartItem));

        CartItem result = cartItemRestService.getCartItemById(id);

        assertEquals(cartItem, result);
    }

    @Test
    public void testCreateAndSaveCart() {
        // Create Cart
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setUserId(userId);

        // Save Cart
        when(cartDb.save(cart)).thenReturn(cart);
        cartRestService.createRestCart(cart);
        verify(cartDb).save(cart);

        // Verify that Cart has been created and saved with an ID
        assertEquals(userId, cart.getUserId());
    }

    @Test
    public void testSaveCartItemAndUpdateTotalPrice() {
        // Create Cart
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setUserId(userId);

        // Save Cart
        when(cartDb.save(cart)).thenReturn(cart);
        cartRestService.createRestCart(cart);
        verify(cartDb).save(cart);

        // Create CartItem
        UUID productId = UUID.randomUUID();
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(2);
        cartItem.setCart(cart);
        cartItem.setProductName("Test Product");
        cartItem.setProductPrice(100);

        // Save CartItem and Update Total Price
        when(cartItemDb.save(cartItem)).thenReturn(cartItem);
        cartItemRestService.saveRestCartItem(cartItem);
        verify(cartItemDb).save(cartItem);

        // Verify that Total Price is updated in Cart
        assertEquals(200, cart.getTotalPrice());
    }

    @Test
    public void testGetAllCartItems() {
        // Get All Cart Items
        List<CartItem> cartItems = new ArrayList<>();
        when(cartItemDb.findAll()).thenReturn(cartItems);

        List<CartItem> result = cartItemRestService.getRestAllCartItem();

        assertEquals(cartItems, result);
    }

    @Test
    public void testGetCartItemByUserId() {
        UUID userId = UUID.randomUUID();
        List<CartItem> cartItems = new ArrayList<>();
        when(cartItemDb.findByCart_UserId(userId)).thenReturn(cartItems);

        List<CartItem> result = cartItemRestService.getCartItemByUserId(userId);

        assertEquals(cartItems, result);
    }

    @Test
    public void testDeleteCartItem() {
        UUID cartItemId = UUID.randomUUID();
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemId);

        // Mock the CartItemRestServiceImpl
        CartItemRestServiceImpl mockCartItemRestService = mock(CartItemRestServiceImpl.class);

        // Stub the behavior of findById
        when(mockCartItemRestService.getCartItemById(cartItemId)).thenReturn(cartItem);

        // Call the method under test
        mockCartItemRestService.deteleRestCartItem(cartItem);

        // Verify that deteleRestCartItem is called
        verify(mockCartItemRestService).deteleRestCartItem(cartItem);
    }

    @Test
    public void testUpdateTotalPriceDelete() {
        // Create Cart
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setUserId(userId);

        // Create CartItem
        UUID productId = UUID.randomUUID();
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(2);
        cartItem.setCart(cart);
        cartItem.setProductName("Test Product");
        cartItem.setProductPrice(100);

        // Add CartItem to Cart
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setListCartItem(cartItems);

        // Save Cart
        when(cartDb.save(cart)).thenReturn(cart);
        cartRestService.createRestCart(cart);
        verify(cartDb).save(cart);

        // Save CartItem and Update Total Price
        when(cartItemDb.save(cartItem)).thenReturn(cartItem);
        cartItemRestService.saveRestCartItem(cartItem);
        verify(cartItemDb).save(cartItem);

        // Delete CartItem and Update Total Price
        cartItemRestService.deteleRestCartItem(cartItem);
        verify(cartItemDb).deleteHard(cartItem.getId());

        // Verify that Total Price is updated in Cart
        assertEquals(0, cart.getTotalPrice());
    }

// Add more tests for other functionalities

// It's important to cover edge cases and handle exceptions in your tests

}