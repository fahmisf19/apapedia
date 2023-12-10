package apap.tk.order.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCartItemRequestDTO {
    @NotNull
    private UUID productId;

    @NotNull
    private Integer quantity = 0;

    @NotNull
    @Size(max = 255)
    private String productName;

    @NotNull
    private Integer productPrice;

    @NotNull
    private UUID cartId;
}
