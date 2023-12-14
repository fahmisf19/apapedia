package apap.tk.frontendweb.service;

import java.util.UUID;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;

public interface CatalogService {
    CatalogDTO addCatalog(CatalogDTO catalogDTO);
    CatalogDTO getCatalogByCatalogId(UUID catalogId);
    CatalogDTO updateCatalog(CatalogDTO catalogDTO);    
}
