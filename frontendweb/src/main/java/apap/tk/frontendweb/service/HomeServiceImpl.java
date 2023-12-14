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
import java.util.*;

@Service
public class HomeServiceImpl implements HomeService {
    private final WebClient webClientCatalog;
    final WebClient webClientOrder;

    private static final String baseUrlCatalog = "http://localhost:8081/api/catalog";
    private static final String baseUrlOrder = "http://localhost:8082/api/order";

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
                
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(response.body(),
                            new TypeReference<Map<Integer, Long>>() {});
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Re-interrupt the thread
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

    @Override
    public List<String> getImage(List<CatalogDTO> catalogList) {
        List<String> imageBase64 = new ArrayList<>();
        for (CatalogDTO catalogDTO : catalogList) {
            imageBase64.add(Base64.getEncoder().encodeToString(catalogDTO.getImage()));
        }
        return imageBase64;
    }
}
