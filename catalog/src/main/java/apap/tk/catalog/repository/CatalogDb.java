package apap.tk.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.catalog.model.Catalog;

import java.util.UUID;
import java.util.List;
 
@Repository
public interface CatalogDb extends JpaRepository<Catalog, UUID>{
    // List<Catalog> findBySellerId(UUID seller);
    List<Catalog> findByProductNameContainingIgnoreCaseOrderByProductName(String productName);
    List<Catalog> findByPriceBetween(Integer lowerLimitPrice, Integer higherLimitPrice);
}
