package apap.tk.frontendweb.restservice;

import apap.tk.frontendweb.dto.auth.request.CreateUserRequestDTO;
import apap.tk.frontendweb.dto.auth.response.ReadUserResponseDTO;

public interface UserRestService {
    String getToken(String username, String password);
    ReadUserResponseDTO createSeller(CreateUserRequestDTO userDTO);
}

