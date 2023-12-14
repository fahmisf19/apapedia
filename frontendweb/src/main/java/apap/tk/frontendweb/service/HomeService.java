package apap.tk.frontendweb.service;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface HomeService {
    Map<Integer, Long> getChartSales(UUID sellerId);
    List<CatalogDTO> getAllCatalog();
    List<CatalogDTO> getCatalogBySellerId(UUID sellerId);
    List<CatalogDTO> searchCatalog(String name);
    List<CatalogDTO> searchCatalogSeller(UUID sellerId, String name);
    List<CatalogDTO> searchCatalogPrice(Integer lowerLimitPrice, Integer higherLimitPrice);
    List<CatalogDTO> searchCatalogPriceSeller(UUID sellerId, Integer lowerLimitPrice, Integer higherLimitPrice);
    List<CatalogDTO> getSortedCatalogList(String sortBy, String sortOrder);
    List<CatalogDTO> getSortedCatalogListSeller(UUID sellerId, String sortBy, String sortOrder);
    List<String> getImage(List<CatalogDTO> catalogDto);
}
