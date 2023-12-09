package apap.tk.catalog.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import apap.tk.catalog.model.Category;

import java.math.BigInteger;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCatalogRequestDTO {
    private String productName;
    private BigInteger price;
    private String productDescription;
    private int stock;
    private byte[] image;
    private Category category;
}
