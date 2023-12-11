package apap.tk.catalog.dto.request;


import java.math.BigInteger;

import apap.tk.catalog.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigInteger;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCatalogDTO extends CreateCatalogRequestDTO {
    private UUID id;

}
