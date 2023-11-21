package apap.tk.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private UUID seller = UUID.randomUUID(); //hrsny merefer ke user
    
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
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // @NotNull
    @Column(name = "image", nullable = true)
    private byte[] image;
    
}

