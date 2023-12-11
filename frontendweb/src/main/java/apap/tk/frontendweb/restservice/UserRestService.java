package apap.tk.frontendweb.restservice;

import apap.tk.frontendweb.dto.auth.request.UserRequestDTO;
import apap.tk.frontendweb.dto.auth.response.UserResponseDTO;

public interface UserRestService {
    String getToken(String username, String name);

    UserResponseDTO sendUser(UserRequestDTO userDTO, String jwtToken);
}

