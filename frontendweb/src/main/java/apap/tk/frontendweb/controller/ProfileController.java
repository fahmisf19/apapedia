package apap.tk.frontendweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
public class ProfileController {

    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8084/api/users";

    public ProfileController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @GetMapping("/profile/withdraw")
    public String showWithdrawForm(Model model) {
        return "profile/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdrawBalanceUser(
            @RequestParam UUID userId,
            @RequestParam Long amount,
            Model model
    ) {
        // Menggunakan WebClient untuk memanggil API users
        String uri = "/update-balance/{userId}?amount={amount}";

        webClient.put()
                .uri(uri, userId, amount)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    model.addAttribute("withdrawStatus", response);
                })
                .block();

        return "status-withdraw";
    }
}
