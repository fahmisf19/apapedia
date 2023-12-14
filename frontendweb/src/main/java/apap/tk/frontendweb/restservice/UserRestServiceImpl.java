package apap.tk.frontendweb.restservice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import apap.tk.frontendweb.dto.auth.request.CreateUserRequestDTO;
import apap.tk.frontendweb.dto.auth.request.LoginJwtRequestDTO;
import apap.tk.frontendweb.dto.auth.response.ReadUserResponseDTO;
import apap.tk.frontendweb.dto.auth.response.LoginJwtResponseDTO;

@Service
public class UserRestServiceImpl implements UserRestService{
    private final WebClient webClient;

    public UserRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8084")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public String getToken(String username, String password) {
        var body = new LoginJwtRequestDTO(username, password);

        var response = this.webClient
                .post()
                .uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(LoginJwtResponseDTO.class)
                .block();

        String token = null;
        if (response != null) {
            token = response.getToken();
        }
        return token;
    }

    @Override
    public ReadUserResponseDTO createSeller(CreateUserRequestDTO userDTO) {
        try {
            var response = this.webClient
                    .post()
                    .uri("/api/user/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(userDTO)
                    .retrieve()
                    .bodyToMono(ReadUserResponseDTO.class);

            return response.block();
        } catch (Exception e) {
            return new ReadUserResponseDTO();
        }
    }
}
