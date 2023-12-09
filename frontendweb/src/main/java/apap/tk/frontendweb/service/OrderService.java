package apap.tk.frontendweb.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import apap.tk.frontendweb.dto.response.order.ReadOrderResponseDTO;

public interface OrderService {
    List<ReadOrderResponseDTO> getListOrder(UUID sellerId) throws IOException, InterruptedException;
    void updateOrderStatus(UUID orderId, Integer newStatus) throws IOException, InterruptedException;
}
