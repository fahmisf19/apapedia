package apap.tk.catalog.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.model.Category;
import apap.tk.catalog.repository.CatalogDb;
import apap.tk.catalog.repository.CategoryDb;
import jakarta.transaction.Transactional;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
@Transactional
public class CatalogRestService {
    @Autowired
    private CatalogDb catalogDb;

    @Autowired
    private CategoryDb categoryDb;

    public List<Catalog> getAllCatalog(){
        return catalogDb.findAll();
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
        Catalog catalog = getRestCatalogById(idCatalog);
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


    public Catalog createRestCatalog(Catalog catalog) { 
        return catalogDb.save(catalog); 
    };

    // public List<Catalog> retrieveListCatalogBySellerId(UUID sellerId) { 
    //     return catalogDb.findBySellerId(sellerId);
    // }

    public List<Catalog> findCatalogByName(String productName){
        return catalogDb.findByProductNameContainingIgnoreCaseOrderByProductName(productName);
    }

    public List<Catalog> findCatalogByPrice(Integer lowerLimitPrice, Integer higherLimitPrice){
        return catalogDb.findByPriceBetween(lowerLimitPrice, higherLimitPrice);
    }


}
