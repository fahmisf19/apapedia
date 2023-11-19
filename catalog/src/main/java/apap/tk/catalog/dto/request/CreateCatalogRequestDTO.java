package apap.tk.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.math.BigInteger;

import apap.tk.catalog.model.Category;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCatalogRequestDTO {
    private String productName;

    private BigInteger price;

    private String productDescription;

    private int stock;

    private MultipartFile image;

    private Category category;

}
