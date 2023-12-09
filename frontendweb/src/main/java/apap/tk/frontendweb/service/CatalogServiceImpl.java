package apap.tk.frontendweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8081/api/catalog/create";

    public CatalogServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public CatalogDTO addCatalog(CatalogDTO request) {
        return webClient.post()
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).map(RuntimeException::new)
                )
                .bodyToMono(CatalogDTO.class)
                .block();
    }
}
