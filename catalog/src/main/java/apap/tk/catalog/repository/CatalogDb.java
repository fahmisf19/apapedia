package apap.tk.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.catalog.model.Catalog;

import java.util.UUID;
 
@Repository
public interface CatalogDb extends JpaRepository<Catalog, UUID>{

}
