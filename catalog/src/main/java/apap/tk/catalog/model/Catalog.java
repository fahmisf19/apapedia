package apap.tk.catalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    private UUID id = UUID.randomUUID();
    
    // msh belom fix
    // @NotNull
    @Column(name = "seller", nullable = true)
    private UUID seller = UUID.randomUUID();

    // @NotNull
    @Column(name = "price", nullable = false)
    private BigInteger price;
    
    // @NotNull
    @Column(name = "productName", nullable = false)
    private String productName;
    
    // @NotNull
    @Column(name = "productDescription", nullable = false)
    private String productDescription;
  
    // @NotNull
    @Column(name = "stock", nullable = false)
    private int stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category", referencedColumnName = "idCategory")
    Category category;

    // @NotNull
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // @NotNull
    @Column(name = "image", nullable = true)
    private byte[] image;
    
}
