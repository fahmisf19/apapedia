package apap.tk.frontendweb.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {
    @Override
    public Map<Integer, Long> getChartSales() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/order/sales-per-day"))
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
}
