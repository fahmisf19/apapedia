package apap.tk.frontendweb.dto.response.order;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadOrderItemResponseDTO {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private String productName;
    private Integer productPrice;
}