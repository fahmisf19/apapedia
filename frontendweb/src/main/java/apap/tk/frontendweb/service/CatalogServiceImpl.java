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
    public List<CatalogDTO> getAllCatalog() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/get-all").build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> getCatalogBySellerId(UUID sellerId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/sellerId/" + sellerId).build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }


    @Override
    public List<CatalogDTO> searchCatalog(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search-catalog-name").queryParam("catalog", name).build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> searchCatalogSeller(UUID sellerId, String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search-catalog-name/" + sellerId).queryParam("catalog", name).build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> searchCatalogPrice(Integer lowerLimitPrice, Integer higherLimitPrice) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search-catalog-price")
                        .queryParam("lowerLimitPrice", lowerLimitPrice)
                        .queryParam("higherLimitPrice", higherLimitPrice)
                        .build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> searchCatalogPriceSeller(UUID sellerId, Integer lowerLimitPrice, Integer higherLimitPrice) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search-catalog-price/" + sellerId)
                        .queryParam("lowerLimitPrice", lowerLimitPrice)
                        .queryParam("higherLimitPrice", higherLimitPrice)
                        .build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }
}
