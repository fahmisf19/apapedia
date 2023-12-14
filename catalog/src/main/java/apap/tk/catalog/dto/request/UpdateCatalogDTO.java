package apap.tk.catalog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCatalogDTO extends CreateCatalogRequestDTO {
    private UUID id;

}
