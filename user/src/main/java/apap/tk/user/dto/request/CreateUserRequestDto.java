package main.java.apap.tk.user.dto.request;

import apap.tk.user.model.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDto {
    @Id
    private UUID userId;
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private Date address;
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
}