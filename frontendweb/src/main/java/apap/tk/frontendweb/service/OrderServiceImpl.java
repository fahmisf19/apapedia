package apap.tk.frontendweb.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import apap.tk.frontendweb.dto.response.order.ReadOrderResponseDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final String urlUser = "http://localhost:8080/api/";
    private final String urlCatalog = "http://localhost:8081/api/";
    private final String urlOrder = "http://localhost:8082/api/";


    private final WebClient webClientUser;
    private final WebClient webClientCatalog;
    private final WebClient webClientOrder;


    public OrderServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientUser = webClientBuilder.baseUrl(urlUser).build();
        this.webClientCatalog = webClientBuilder.baseUrl(urlCatalog).build();
        this.webClientOrder = webClientBuilder.baseUrl(urlOrder).build();
    }

    @Override
    public List<ReadOrderResponseDTO> getListOrder(UUID sellerId) throws IOException, InterruptedException {
        return webClientOrder.get()
        .uri(uriBuilder -> uriBuilder.path("order/getBySellerId").queryParam("sellerId", sellerId).build())
        .retrieve()
        .bodyToFlux(ReadOrderResponseDTO.class)
        .collectList()
        .block();
    }

    @Override
    public void updateOrderStatus(UUID orderId, Integer newStatus) throws IOException, InterruptedException {
        webClientOrder.put()
                .uri(uriBuilder -> uriBuilder.path("order/{orderId}/update")
                        .queryParam("newStatus", newStatus)
                        .build(orderId))  // Menggunakan build(orderId) untuk mengisi nilai orderId ke dalam URI
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
