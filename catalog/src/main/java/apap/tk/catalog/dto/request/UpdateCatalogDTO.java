package apap.tk.catalog.dto.request;

import apap.tk.catalog.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCatalogDTO {
    private String productName;
    private int price;
    private String productDescription;
    private int stock;
    private byte[] image;
    private Category category;

}
