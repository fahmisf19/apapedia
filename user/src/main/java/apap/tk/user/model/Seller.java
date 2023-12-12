package apap.tk.user.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "seller")
public class Seller extends UserEntity {
    
    @NotNull
    @Column(name = "category", nullable = false)
    private String category;
}