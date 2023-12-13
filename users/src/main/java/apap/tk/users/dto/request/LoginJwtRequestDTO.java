package apap.tk.users.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginJwtRequestDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
