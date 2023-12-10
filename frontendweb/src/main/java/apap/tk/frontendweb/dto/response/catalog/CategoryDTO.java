package apap.tk.frontendweb.dto.response.catalog;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO{
    private UUID idCategory;
    private String name;
}
