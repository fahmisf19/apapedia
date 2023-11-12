package main.java.apap.tk.user.restservice;

import apap.tk.order.model.Order;
import apap.tk.user.model.User;
import apap.tk.user.repository.UserDb;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.UUID;
import java.util.List;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    
    @Autowired
    private UserDb userDb;

    // User Service #2: POST user (sign up)
    @Override
    public void createRestUser(User user) {
        userDb.save(user);
    }

    @Override
    public List<User> retrieveRestAllUser() {
        return userDb.findAll();
    }

    // User Service #1: GET User by Id
    @Override
    public User getUserRestById(UUID id) {
        for (User user : retrieveRestAllUser()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
