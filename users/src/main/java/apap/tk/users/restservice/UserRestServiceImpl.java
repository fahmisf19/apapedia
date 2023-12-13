package apap.tk.users.restservice;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import apap.tk.users.dto.request.CreateCartRequestDTO;
import apap.tk.users.dto.request.LoginJwtRequestDTO;
import apap.tk.users.model.Customer;
import apap.tk.users.model.Seller;
import apap.tk.users.model.User;
import apap.tk.users.repository.CustomerDb;
import apap.tk.users.repository.SellerDb;
import apap.tk.users.repository.UserDb;
import apap.tk.users.security.jwt.JwtUtils;

@Service
public class UserRestServiceImpl implements UserRestService{
    @Autowired
    private UserDb userDb;

    @Autowired
    private SellerDb sellerDb;

    @Autowired
    private CustomerDb customerDb;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Seller createSeller (Seller seller) {
        sellerDb.save(seller);
        return seller;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        customer.setPassword(encrypt(customer.getPassword()));
        customerDb.save(customer);
    
        // Set up the request body
        CreateCartRequestDTO cartDTO = new CreateCartRequestDTO(customer.getCartId(), customer.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateCartRequestDTO> requestEntity = new HttpEntity<>(cartDTO, headers);
    
        RestTemplate restTemplate = new RestTemplate();
        try {
            ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<Map<String, Object>>() {};
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "http://localhost:8082/api/cart/create",
                HttpMethod.POST,
                requestEntity,
                responseType
            );
    
            // Extract cartId from the response and set it to the customer
            String cartIdString = (String) response.getBody().get("id");
            UUID cartId = UUID.fromString(cartIdString);
            customer.setCartId(cartId);
            customerDb.save(customer); // Save the updated customer entity with cartId
        } catch (RestClientException e) {
            customerDb.delete(customer);
            throw new RestClientException("Failed to create user's cart: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public User getUserById(UUID id) {
        Optional<User> userOptional = userDb.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getDeleted()) {
                return user;
            } else {
                throw new NoSuchElementException("User not found");
            }
        } else {
            throw new NoSuchElementException("User not found");
        }
    }    

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public String loginJwtAdmin(LoginJwtRequestDTO loginJwtRequestDTO) {
        String username = loginJwtRequestDTO.getUsername();

        var user = userDb.findByUsername(username);

        if (user != null) {
            return jwtUtils.generateJwtToken(loginJwtRequestDTO.getUsername());
        } else {
            throw new NoSuchElementException("User not found");
        }
    }
}