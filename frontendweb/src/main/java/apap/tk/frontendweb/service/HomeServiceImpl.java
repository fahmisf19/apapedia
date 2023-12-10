package apap.tk.frontendweb.service;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HomeServiceImpl implements HomeService {
    private final WebClient webClientCatalog;
    private final WebClient webClientOrder;

    private final String baseUrlCatalog = "http://localhost:8081/api/catalog";
    private final String baseUrlOrder = "http://localhost:8082/api/order";

    public HomeServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientCatalog = webClientBuilder.baseUrl(baseUrlCatalog).build();
        this.webClientOrder = webClientBuilder.baseUrl(baseUrlOrder).build();
    }

    @Override
    public Map<Integer, Long> getChartSales(UUID sellerId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/order/quantity-per-day/"+sellerId))
                .header("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());

            // Assuming the response body is a JSON string like {"1": 0, "2": 0, ...}
            ObjectMapper objectMapper = new ObjectMapper();
            Map<Integer, Long> salesPerDay = objectMapper.readValue(response.body(),
                    new TypeReference<Map<Integer, Long>>() {
                    });

            return salesPerDay;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CatalogDTO> getAllCatalog() {
        return webClientCatalog.get()
                .uri(uriBuilder -> uriBuilder.path("/getAll").build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> getCatalogBySellerId(UUID sellerId) {
        return webClientCatalog.get()
                .uri(uriBuilder -> uriBuilder.path("/sellerId/" + sellerId).build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }


    @Override
    public List<CatalogDTO> searchCatalog(String name) {
        return webClientCatalog.get()
                .uri(uriBuilder -> uriBuilder.path("/search-catalog-name").queryParam("catalog", name).build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> searchCatalogSeller(UUID sellerId, String name) {
        return webClientCatalog.get()
                .uri(uriBuilder -> uriBuilder.path("/search-catalog-name/" + sellerId).queryParam("catalog", name).build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> searchCatalogPrice(Integer lowerLimitPrice, Integer higherLimitPrice) {
        return webClientCatalog.get()
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
        return webClientCatalog.get()
                .uri(uriBuilder -> uriBuilder.path("/search-catalog-price/" + sellerId)
                        .queryParam("lowerLimitPrice", lowerLimitPrice)
                        .queryParam("higherLimitPrice", higherLimitPrice)
                        .build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> getSortedCatalogList(String sortBy, String sortOrder) {
        return webClientCatalog.get()
                .uri(uriBuilder -> uriBuilder.path("/sort-by")
                        .queryParam("sortBy", sortBy)
                        .queryParam("sortOrder", sortOrder)
                        .build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }

    @Override
    public List<CatalogDTO> getSortedCatalogListSeller(UUID sellerId, String sortBy, String sortOrder) {
        return webClientCatalog.get()
                .uri(uriBuilder -> uriBuilder.path("/sort-by/" + sellerId)
                        .queryParam("sortBy", sortBy)
                        .queryParam("sortOrder", sortOrder)
                        .build())
                .retrieve()
                .bodyToFlux(CatalogDTO.class)
                .collectList()
                .block();
    }
}
