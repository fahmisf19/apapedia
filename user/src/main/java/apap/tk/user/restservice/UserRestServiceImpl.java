package apap.tk.user.restservice;
import apap.tk.user.model.UserEntity;
import apap.tk.user.repository.UserDb;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    
    @Autowired
    private UserDb userDb;

    // User Service #2: POST user (sign up)
    @Override
    public void createRestUser(UserEntity user) {
        userDb.save(user);
    }

    @Override
    public List<UserEntity> retrieveRestAllUser() {
        return userDb.findAll();
    }

    // User Service #1: GET User by Id
    @Override
    public UserEntity getRestUserById(UUID id) {
        Optional<UserEntity> user = userDb.findById(id);
        return user.get();
    }

    // @Override
    // public Optional<User> getRestUserById(UUID id){
    // //     for (User user : retrieveRestAllUser()) {
    // //         if (user.getId().equals(id)) {
    // //             return user;
    // //         }
    // //     }
    // //     return id;
    //     return userDb.findById(id);
    // }


}
