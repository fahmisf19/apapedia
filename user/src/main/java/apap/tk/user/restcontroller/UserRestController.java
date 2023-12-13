package apap.tk.user.restcontroller;

import apap.tk.user.model.Role;
import apap.tk.user.model.UserEntity;
import apap.tk.user.repository.RoleDb;
import apap.tk.user.dto.UserMapper;
import apap.tk.user.dto.request.CreateSellerRequestDTO;
import apap.tk.user.dto.request.CreateUserRequestDto;
import apap.tk.user.restservice.UserRestService;
import jakarta.validation.Valid;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class UserRestController {
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private apap.tk.user.restservice.UserRestService userRestService;

    @Autowired
    private RoleDb roleDb;

    // User Service #1: GET User by Id
    @GetMapping(value = "user/{id}")
    private UserEntity retrieveUser(@PathVariable("id") String id) {
        try {
            return userRestService.getRestUserById(UUID.fromString(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID User " + id + " not found");
        }
    }

    // User Service #2: POST User (sign up)
    @PostMapping(value = "user/create")
    public void restCreateUser(@Valid @RequestBody CreateUserRequestDto userDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");
        } else {
            userDTO.setCreatedAt(new Date());
            userDTO.setUpdatedAt(new Date());
            UserEntity user = userMapper.createUserRequestDTOToUser(userDTO);
            userRestService.createRestUser(user);
        }
    }

    @PostMapping(value = "seller/create")
    public void restCreateSeller(@Valid @RequestBody CreateSellerRequestDTO sellerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");
        } else {
            // Set role "Seller" to the seller
            Role sellerRole = roleDb.findByRole("Seller")
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found"));
    
            UserEntity seller = userMapper.createSellerRequestDTOToUser(sellerDTO);
            seller.setRoleid(sellerRole.getId()); // Set seller role
            seller.setCreatedAt(new Date());
            seller.setUpdatedAt(new Date());
            seller.setPassword("dummy");
            userRestService.createRestUser(seller);
        }
    }
    
}
