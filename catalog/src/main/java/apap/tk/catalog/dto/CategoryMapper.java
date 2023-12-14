package apap.tk.catalog.dto;

import org.mapstruct.Mapper;

import apap.tk.catalog.dto.request.CreateCategoryRequestDTO;
import apap.tk.catalog.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category createCategoryRequestDTOToCategory(CreateCategoryRequestDTO createCategoryRequestDTO);
}
