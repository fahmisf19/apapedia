package main.java.apap.tk.user.restcontroller;

import apap.tk.user.model.User;
import main.java.apap.tk.user.dto.UserMapper;
import apap.tk.user.dto.request.CreateUserRequestDto;
import apap.tk.user.restservice.UserRestService;

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
    private main.java.apap.tk.user.restservice.UserRestService userRestService;

    // User Service #1: GET User by Id
    @GetMapping(value = "user/{id}")
    private User retrieveUser(@PathVariable("id") String id) {
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
            User user = userMapper.createUserRequestDtoToUser(userDTO);
            userRestService.createRestUser(user);
        }
    }
}
