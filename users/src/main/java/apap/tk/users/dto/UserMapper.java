package apap.tk.users.dto;

import org.mapstruct.Mapper;

import apap.tk.users.dto.request.CreateUserRequestDTO;
import apap.tk.users.model.Customer;
import apap.tk.users.model.Seller;
import apap.tk.users.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User createUserRequestDTOtoUser(CreateUserRequestDTO createUserRequestDTO);
    
    Customer userRequestDTOToCustomer(CreateUserRequestDTO userDTO);

    Seller userRequestDTOToSeller(CreateUserRequestDTO userDTO);
}
