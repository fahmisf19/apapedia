package apap.tk.catalog.dto;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import apap.tk.catalog.dto.request.CreateCatalogRequestDTO;
import apap.tk.catalog.dto.request.UpdateCatalogDTO;
import apap.tk.catalog.model.Catalog;
import java.io.IOException;


@Mapper(componentModel = "spring")
public interface CatalogMapper {
     Catalog updateCatalogRequestDTOToCatalog(UpdateCatalogDTO updateCatalogDTO);

    @Mapping(target = "image", ignore = true)
    Catalog CreateCatalogRequestDTOToCatalog(CreateCatalogRequestDTO createCatalogRequestDTO);   

    @AfterMapping
    default void mapImage(@MappingTarget Catalog catalog, CreateCatalogRequestDTO createCatalogRequestDTO) {
        if (createCatalogRequestDTO.getImage() != null) {
            try {
                catalog.setImage(createCatalogRequestDTO.getImage().getBytes());
            } catch (IOException e) {
                System.err.println("Error while converting image to bytes: " + e.getMessage());
            }
        }
    }
}
