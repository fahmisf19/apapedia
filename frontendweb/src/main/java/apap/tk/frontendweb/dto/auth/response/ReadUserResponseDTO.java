package apap.tk.frontendweb.dto.auth.response;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadUserResponseDTO {
    private UUID id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Long balance;
    private String address;
    private Date created_at;
    private Date updated_at;
    private String role;
    private String category;
}