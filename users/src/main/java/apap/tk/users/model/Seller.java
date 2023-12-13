package apap.tk.users.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name="id_user")
@Entity
public class Seller extends User {
    
    @Column(name="category", nullable=false)
    private String category;
}
