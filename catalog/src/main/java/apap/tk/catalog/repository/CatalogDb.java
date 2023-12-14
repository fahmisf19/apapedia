package apap.tk.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.catalog.model.Catalog;

import java.util.UUID;
import java.util.List;
 
@Repository
public interface CatalogDb extends JpaRepository<Catalog, UUID>{
    List<Catalog> findBySellerOrderByProductName(UUID seller);
    List<Catalog> findAllByOrderByProductName();
    List<Catalog> findByProductNameContainingIgnoreCaseOrderByProductName(String productName);
    List<Catalog> findBySellerAndProductNameContainingIgnoreCaseOrderByProductName(UUID seller, String productName);
    List<Catalog> findByPriceBetween(Integer lowerLimitPrice, Integer higherLimitPrice);
    List<Catalog> findBySellerAndPriceBetween(UUID sellerId, Integer lowerLimitPrice, Integer higherLimitPrice);
    List<Catalog> findBySellerOrderByProductNameDesc(UUID sellerId);
    List<Catalog> findBySellerOrderByPrice(UUID sellerId);
    List<Catalog> findBySellerOrderByPriceDesc(UUID sellerId);
}
