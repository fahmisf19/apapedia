package main.java.apap.tk.user.model;

import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer extends User {
    
    @NotNull
    @Column(name = "cart_id", nullable = false)
    private UUID cartId;
}
