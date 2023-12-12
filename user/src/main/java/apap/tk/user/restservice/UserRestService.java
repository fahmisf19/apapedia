package apap.tk.user.restservice;

import apap.tk.user.model.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRestService {
    void createRestUser(UserEntity user);
    List<UserEntity> retrieveRestAllUser();
    UserEntity getRestUserById(UUID id);
}
