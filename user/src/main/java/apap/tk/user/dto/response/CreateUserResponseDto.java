package apap.tk.user.dto.response;

import java.util.Date;
import java.util.UUID;

import apap.tk.user.model.Role;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseDto {
    private UUID userId;
    private String name;
    private Role role;
    private String username;
    private String password;
    private String email;
    private Date address;
    private Date createdAt;
    private Date updatedAt;
}
