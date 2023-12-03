package apap.tk.frontendweb.dto.response.order;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadOrderResponseDTO {
    private UUID id = UUID.randomUUID();
    private Date createdAt;
    private Date updateAt;
    private Integer status;
    private Integer totalPrice = 0;
    private UUID customerId;
    private UUID sellerId;
    private List<ReadOrderItemResponseDTO> listOrderItem;
}