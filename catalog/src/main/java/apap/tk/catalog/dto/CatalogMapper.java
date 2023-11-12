package apap.tk.catalog.dto;

import org.mapstruct.Mapper;

import apap.tk.catalog.dto.request.UpdateCatalogDTO;
import apap.tk.catalog.model.Catalog;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    Catalog updateCatalogRequestDTOToCatalog(UpdateCatalogDTO updateCatalogDTO);

}
