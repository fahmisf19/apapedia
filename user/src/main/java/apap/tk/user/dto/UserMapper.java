package apap.tk.user.dto;

import apap.tk.user.dto.request.CreateSellerRequestDTO;
import apap.tk.user.dto.request.CreateUserRequestDto;
import apap.tk.user.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity createUserRequestDTOToUser(CreateUserRequestDto createUserRequestDto);
    UserEntity createSellerRequestDTOToUser(CreateSellerRequestDTO createSellerRequestDTO);
}