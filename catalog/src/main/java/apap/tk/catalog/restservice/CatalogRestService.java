package apap.tk.catalog.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.model.Category;
import apap.tk.catalog.repository.CatalogDb;
import apap.tk.catalog.repository.CategoryDb;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatalogRestService {
    @Autowired
    private CatalogDb catalogDb;

    @Autowired
    private CategoryDb categoryDb;

    public List<Catalog> getAllCatalog(){
        return catalogDb.findAllByOrderByProductName();
    }

    public Catalog getRestCatalogById(UUID id){
        for(Catalog catalog : getAllCatalog()){
            if (catalog.getId().equals(id)) {
                return catalog;
            }
        }
        return null;
    }

    public Catalog updateRestCatalog(UUID idCatalog, Catalog catalogFromDto) {
        var catalog = getRestCatalogById(idCatalog);
        if (catalog != null) {
            var category = new Category();
            catalog.setProductName(catalogFromDto.getProductName());
            catalog.setPrice(catalogFromDto.getPrice());
            catalog.setProductDescription(catalogFromDto.getProductDescription());
            catalog.setStock(catalogFromDto.getStock());
            catalog.setImage(catalogFromDto.getImage());
            category.setIdCategory(catalogFromDto.getCategory().getIdCategory());
            category.setName(catalogFromDto.getCategory().getName());
            categoryDb.save(category);
            catalog.setCategory(category);
            catalogDb.save(catalog);
            
        }
        return catalog;
    }

    public void deleteCatalog(Catalog catalog) {
        catalog.setDeleted(true);
        catalogDb.save(catalog);
    }


    public void createRestCatalog(Catalog catalog) { 
        catalogDb.save(catalog); 
    };

     public List<Catalog> retrieveListCatalogBySellerId(UUID sellerId) {
         return catalogDb.findBySellerOrderByProductName(sellerId);
     }

    public List<Catalog> findCatalogByName(String productName){
        return catalogDb.findByProductNameContainingIgnoreCaseOrderByProductName(productName);
    }

    public List<Catalog> findCatalogBySellerAndName(UUID seller, String productName){
        return catalogDb.findBySellerAndProductNameContainingIgnoreCaseOrderByProductName(seller, productName);
    }

    public List<Catalog> findCatalogByPrice(Integer lowerLimitPrice, Integer higherLimitPrice){
        return catalogDb.findByPriceBetween(lowerLimitPrice, higherLimitPrice);
    }

    public List<Catalog> getAllCatalogSorted(String sortBy, String sortOrder) {
        // Menentukan metode sort berdasarkan harga atau nama
        var sort = Sort.by(sortOrder.equals("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));

        // Mengambil semua katalog dari database dengan urutan yang ditentukan
        return catalogDb.findAll(sort);
    }
    
    public List<Catalog> findCatalogBySellerAndPrice(UUID sellerId, Integer lowerLimitPrice, Integer higherLimitPrice){
        return catalogDb.findBySellerAndPriceBetween(sellerId, lowerLimitPrice, higherLimitPrice);
    }

    public List<Catalog> getSellerSortedCatalogList(UUID sellerId, String sortBy, String sortOrder) {
        List<Catalog> catalogList;
        if("price".equalsIgnoreCase(sortBy)) {
            if("asc".equalsIgnoreCase(sortOrder)) {
                catalogList = catalogDb.findBySellerOrderByPrice(sellerId);
            } else {
                catalogList = catalogDb.findBySellerOrderByPriceDesc(sellerId);
            }
        } else if("productName".equalsIgnoreCase(sortBy)) {
            if("asc".equalsIgnoreCase(sortOrder)) {
                catalogList = catalogDb.findBySellerOrderByProductName(sellerId);
            } else {
                catalogList = catalogDb.findBySellerOrderByProductNameDesc(sellerId);
            }
        } else {
            catalogList = catalogDb.findAll();
        }
        return catalogList;
    }
}
