package apap.tk.order.dto.request;

import apap.tk.order.model.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderRequestDTO {
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updateAt;
    @NotNull
    private Integer status;
    @NotNull
    private Integer totalPrice;
    @NotNull
    private UUID customerId;
    @NotNull
    private UUID sellerId;
    @NotNull
    private List<OrderItem> listOrderItem = new ArrayList<>();
}
