package apap.tk.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="order_item")
public class OrderItem {
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Size(max = 255)
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;
}
