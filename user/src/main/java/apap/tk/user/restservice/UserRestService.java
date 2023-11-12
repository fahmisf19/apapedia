package main.java.apap.tk.user.restservice;

import apap.tk.user.model.User;
import java.util.List;

public interface UserRestService {
    void createRestUser(User user);
    List<User> retrieveRestAllUser();
    User getRestUserById(UUID id);
}
