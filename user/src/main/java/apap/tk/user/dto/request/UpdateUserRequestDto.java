package apap.tk.user.dto.request;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequestDto extends CreateUserRequestDto {
    private UUID id;
    private long balance;
    private Date updatedAt;

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}
