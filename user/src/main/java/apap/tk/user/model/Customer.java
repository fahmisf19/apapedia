package apap.tk.user.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customer")
public class Customer extends UserEntity {
    
    @NotNull
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;
}
