package apap.tk.catalog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import apap.tk.catalog.model.Category;

import java.math.BigInteger;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCatalogRequestDTO {
    private String productName;
    private UUID seller;
    private BigInteger price;
    private String productDescription;
    private int stock;
    private byte[] image;
    private Category category;
}
