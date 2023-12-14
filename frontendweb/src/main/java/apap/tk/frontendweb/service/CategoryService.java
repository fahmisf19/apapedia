package apap.tk.frontendweb.service;

import java.util.List;

import apap.tk.frontendweb.dto.response.catalog.CategoryDTO;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
}
