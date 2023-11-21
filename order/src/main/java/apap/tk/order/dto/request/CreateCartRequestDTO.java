package apap.tk.order.dto.request;

// import java.util.ArrayList;
// import java.util.List;
import java.util.UUID;

// import apap.tk.order.model.CartItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateCartRequestDTO {
    @NotNull
    private UUID userId;
}
