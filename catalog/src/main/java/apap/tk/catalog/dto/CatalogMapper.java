package apap.tk.catalog.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import apap.tk.catalog.dto.request.CreateCatalogRequestDTO;
import apap.tk.catalog.dto.request.UpdateCatalogDTO;
import apap.tk.catalog.model.Catalog;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    Catalog updateCatalogRequestDTOToCatalog(UpdateCatalogDTO updateCatalogDTO);

    @Mapping(target = "image", ignore = true)
    Catalog createCatalogRequestDTOToCatalog(CreateCatalogRequestDTO createCatalogRequestDTO);   
}
