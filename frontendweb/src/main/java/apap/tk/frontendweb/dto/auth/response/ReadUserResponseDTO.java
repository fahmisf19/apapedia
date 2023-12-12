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
    private String address;
    private Long role;
    private Date updated_at;
    private Date created_at;
    private String category;
}
