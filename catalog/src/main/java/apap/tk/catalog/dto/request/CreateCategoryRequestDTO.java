package apap.tk.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import apap.tk.catalog.model.Category;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCategoryRequestDTO {
    private String name; 

    private List<Category> listCategory;
}
