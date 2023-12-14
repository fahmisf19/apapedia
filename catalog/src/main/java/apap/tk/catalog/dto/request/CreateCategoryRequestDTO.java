package apap.tk.catalog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import apap.tk.catalog.model.Category;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCategoryRequestDTO {
    private String name; 

    private List<Category> listCategory;
}
