package apap.tk.user.dto.request;

import apap.tk.user.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequestDto {

    @NotNull
    private String name;
    @NotNull
    private Role role;
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