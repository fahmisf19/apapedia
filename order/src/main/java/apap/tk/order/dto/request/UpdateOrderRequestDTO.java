package apap.tk.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderRequestDTO extends CreateOrderRequestDTO {
    @NotNull
    private UUID id;
}
