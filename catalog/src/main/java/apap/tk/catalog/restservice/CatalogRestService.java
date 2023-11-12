package apap.tk.catalog.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.repository.CatalogDb;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CatalogRestService {
    @Autowired
    private CatalogDb catalogDb;

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
            catalog.setProductName(catalogFromDto.getProductName());
            catalog.setPrice(catalogFromDto.getPrice());
            catalog.setProductDescription(catalogFromDto.getProductDescription());
            catalog.setStock(catalogFromDto.getStock());
            catalog.setImage(catalogFromDto.getImage());
            catalog.setCategory(catalogFromDto.getCategory());
            catalogDb.save(catalog);
            
        }
        return catalog;
    }

    public void deleteCatalog(Catalog catalog) {
        catalog.setDeleted(true);
        catalogDb.save(catalog);
    }
}
