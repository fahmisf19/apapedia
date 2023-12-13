package apap.tk.users.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apap.tk.users.dto.request.LoginJwtRequestDTO;
import apap.tk.users.dto.request.LoginJwtRequestFlutterDTO;
import apap.tk.users.dto.response.LoginJwtResponseDTO;
import apap.tk.users.restservice.UserRestService;
import apap.tk.users.security.jwt.JwtUtils;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("/api")
public class AuthRestController {
    @Autowired
    UserRestService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> loginJwtAdmin (@RequestBody LoginJwtRequestDTO loginJwtRequestDTO) {
        try {
            String jwtToken = userService.loginJwtAdmin(loginJwtRequestDTO);
            return new ResponseEntity<>(new LoginJwtResponseDTO(jwtToken), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // API for flutter to authenticate user credentials and generate jwt token
    @PostMapping("/auth/login-flutter")
    public ResponseEntity<?> loginJwtFlutter(@RequestBody LoginJwtRequestFlutterDTO loginJwtRequestFlutterDTO) {
        try{
            authenticate(loginJwtRequestFlutterDTO.getUsername(), loginJwtRequestFlutterDTO.getPassword());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        final String token = jwtUtils.generateJwtToken(loginJwtRequestFlutterDTO.getUsername());
        return ResponseEntity.ok(new LoginJwtResponseDTO(token));
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(encoder);
        AuthenticationManager authenticationManager = new ProviderManager(authenticationProvider);

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch (Exception e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
