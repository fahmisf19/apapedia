package apap.tk.catalog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.catalog.model.Catalog;

@Repository
public interface CatalogDb extends JpaRepository<Catalog, UUID>{
    
}
