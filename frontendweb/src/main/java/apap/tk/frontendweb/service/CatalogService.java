package apap.tk.frontendweb.service;

import java.util.List;
import java.util.UUID;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import apap.tk.frontendweb.dto.response.catalog.CategoryDTO;

public interface CatalogService {
    CatalogDTO addCatalog(CatalogDTO catalogDTO);
    List<CatalogDTO> getAllCatalog();
    List<CatalogDTO> getCatalogBySellerId(UUID sellerId);
    List<CatalogDTO> searchCatalog(String name);
    List<CatalogDTO> searchCatalogSeller(UUID sellerId, String name);
    List<CatalogDTO> searchCatalogPrice(Integer lowerLimitPrice, Integer higherLimitPrice);
    List<CatalogDTO> searchCatalogPriceSeller(UUID sellerId, Integer lowerLimitPrice, Integer higherLimitPrice);
}
