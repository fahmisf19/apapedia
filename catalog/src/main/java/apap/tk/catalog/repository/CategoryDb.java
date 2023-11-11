package apap.tk.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.model.Category;

import java.util.UUID;
 
@Repository
public interface CategoryDb extends JpaRepository<Category, UUID>{

}
