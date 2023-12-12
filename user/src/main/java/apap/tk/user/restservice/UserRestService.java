package apap.tk.user.restservice;

<<<<<<< HEAD
import apap.tk.user.dto.request.UpdateUserRequestDto;
=======
>>>>>>> d31e0feb0e76c049ff3e109873f2753fdc21bff3
import apap.tk.user.model.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRestService {
    void createRestUser(UserEntity user);
    List<UserEntity> retrieveRestAllUser();
    UserEntity getRestUserById(UUID id);
<<<<<<< HEAD
    // UserEntity updateUser(UpdateUserRequestDto updatedUser, String token);
    void deleteUser(UUID id, String token);
=======
>>>>>>> d31e0feb0e76c049ff3e109873f2753fdc21bff3
}
