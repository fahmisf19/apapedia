package apap.tk.users.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apap.tk.users.dto.request.LoginJwtRequestDTO;
import apap.tk.users.dto.response.LoginJwtResponseDTO;
import apap.tk.users.restservice.UserRestService;

@RestController
@RequestMapping("/api")
public class AuthRestController {
    @Autowired
    UserRestService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginJwtAdmin (@RequestBody LoginJwtRequestDTO loginJwtRequestDTO) {
        try {
            String jwtToken = userService.loginJwtAdmin(loginJwtRequestDTO);
            return new ResponseEntity<>(new LoginJwtResponseDTO(jwtToken), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
