package apap.tk.order.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCartItemRequestDTO{
    @NotNull
    private UUID cartItemId;
    private Integer newQuantity;    
}
