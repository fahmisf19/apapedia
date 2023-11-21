package apap.tk.order.model;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cart_item")
public class CartItem {
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;
}