package apap.tk.frontendweb.dto.response.catalog;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDTO {
    private UUID id;
    private BigInteger price;
    private String productName;
    private String productDescription;
    private int stock;
    private byte[] image; // Tambahkan atribut baru untuk representasi Base64 dari gambar
    private CategoryDTO category;
}

