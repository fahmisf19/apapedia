package apap.tk.users.restcontroller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apap.tk.users.dto.UserMapper;
import apap.tk.users.dto.request.CreateUserRequestDTO;
import apap.tk.users.model.User;
import apap.tk.users.restservice.UserRestService;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private UserRestService userRestService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/user/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDTO userDTO) {
        try {
            if (userDTO.getRole().equals("SELLER")) {
                var seller = userMapper.userRequestDTOToSeller(userDTO);
                userRestService.createSeller(seller);
                return new ResponseEntity<>(seller, HttpStatus.CREATED);
            } else {
                var customer = userMapper.userRequestDTOToCustomer(userDTO);
                userRestService.createCustomer(customer);

                return new ResponseEntity<>(customer, HttpStatus.CREATED);

            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") UUID userId) {
        var user = userRestService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    
}
