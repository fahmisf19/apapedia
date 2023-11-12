package main.java.apap.tk.user.model;

import java.util.Date;

@Entity
@Table(name = "seller")
public class Seller extends User {
    
    @NotNull
    @Column(name = "category", nullable = false)
    private String category;
}