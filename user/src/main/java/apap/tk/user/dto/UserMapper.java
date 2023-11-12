package main.java.apap.tk.user.dto;

import apap.tk.user.dto.request.CreateUserRequestDto;
import apap.tk.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User createOrderRequestDTOToUser(CreateUserRequestDto createUserRequestDto);
}