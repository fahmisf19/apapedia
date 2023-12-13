package apap.tk.users.restservice;

import java.util.UUID;

import apap.tk.users.dto.request.LoginJwtRequestDTO;
import apap.tk.users.model.Customer;
import apap.tk.users.model.Seller;
import apap.tk.users.model.User;

public interface UserRestService {
    Seller createSeller (Seller seller);
    Customer createCustomer (Customer customer);
    User getUserById(UUID id);
    String encrypt(String password);
    String loginJwtAdmin(LoginJwtRequestDTO loginJwtRequestDTO);
}
