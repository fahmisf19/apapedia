package apap.tk.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    private UUID id = UUID.randomUUID();
    
    // msh belom pix
    @NotNull
    @Column(name = "seller", nullable = false)
    private UUID seller;
    
    @NotNull
    @Column(name = "price", nullable = false)
    private int price;
    
    @NotNull
    @Column(name = "productName", nullable = false)
    private String productName;
    
    @NotNull
    @Column(name = "productDescription", nullable = false)
    private String productDescription;
  
    @NotNull
    @Column(name = "stock", nullable = false)
    private int stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category", referencedColumnName = "idCategory")
    Category category;

    @NotNull
    @Column(name = "image", nullable = false)
    private byte[] image;
    
}
