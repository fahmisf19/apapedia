package apap.tk.frontendweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import org.springframework.web.util.UriBuilder;

import java.util.List;
import java.util.UUID;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8081/api/catalog";

    public CatalogServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public CatalogDTO addCatalog(CatalogDTO request) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/create").build())
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).map(RuntimeException::new)
                )
                .bodyToMono(CatalogDTO.class)
                .block();
    }

    @Override
    public CatalogDTO getCatalogByCatalogId(UUID catalogId) {
        return webClient.get()
                .uri("/getByCatalogId?catalogId={catalogId}", catalogId)
                .retrieve()
                .bodyToMono(CatalogDTO.class)
                .block();
    }

    @Override
    public CatalogDTO updateCatalog(CatalogDTO request) {
        UUID idCatalog = request.getId(); // Ambil ID dari objek CatalogDTO
        return webClient.put()
                .uri(uriBuilder -> uriBuilder.path("/{id}/update").build(idCatalog))
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
