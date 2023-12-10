package apap.tk.order.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name="cart")
@JsonIgnoreProperties(value = {"listCartItem"}, allowSetters = true)
public class Cart {
    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice = 0;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    private List<CartItem> listCartItem = new ArrayList<>();
}
