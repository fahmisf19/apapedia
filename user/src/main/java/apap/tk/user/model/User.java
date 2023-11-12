package main.java.apap.tk.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user")
public class User {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(unique=true)
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(unique=true)
    @Column(name = "password", nullable = false)
    private String password;
    // TODO: encrypt password with jwt token

    @NotNull
    @Column(unique=true)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "address", nullable = false)
    private Date address;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}