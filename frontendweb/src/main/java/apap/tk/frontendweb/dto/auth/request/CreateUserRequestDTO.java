package apap.tk.frontendweb.dto.auth.request;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    @NotNull
    private String name;
    @NotNull
    private Long role;
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
