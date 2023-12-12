package apap.tk.user.restservice;
<<<<<<< HEAD
import apap.tk.user.dto.request.UpdateUserRequestDto;
=======
>>>>>>> d31e0feb0e76c049ff3e109873f2753fdc21bff3
import apap.tk.user.model.UserEntity;
import apap.tk.user.repository.UserDb;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.UUID;
import java.util.List;
<<<<<<< HEAD
import java.util.NoSuchElementException;
=======
>>>>>>> d31e0feb0e76c049ff3e109873f2753fdc21bff3
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

<<<<<<< HEAD
    // User Service #4: PUT Ubah Data User
    // @Override
    // public UserEntity updateUser(UpdateUserRequestDto updatedUser, String token) {
    //     if (isSameUser(updatedUser.getId(), token)){
    //         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this user");
    //     }

    //     UserEntity oldUser = userDb.findById(updatedUser.getId()).get();
    //     oldUser.setName(updatedUser.getName());
    //     oldUser.setUsername(updatedUser.getUsername());
    //     oldUser.setPassword(encrypt(updatedUser.getPassword()));
    //     oldUser.setEmail(updatedUser.getEmail());
    //     oldUser.setBalance(updatedUser.getBalance());
    //     oldUser.setAddress(updatedUser.getAddress());
    //     oldUser.setUpdatedAt(updatedUser.getUpdatedAt());
    //     return userDb.save(oldUser);
    // }

    // User Service #5: DELETE User
    @Override
    public void deleteUser(UUID id, String token) {
        // Add Condition and Exception
        userDb.deleteById(id);
    }

=======
>>>>>>> d31e0feb0e76c049ff3e109873f2753fdc21bff3
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
