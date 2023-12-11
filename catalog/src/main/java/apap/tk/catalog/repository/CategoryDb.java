package apap.tk.catalog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tk.catalog.model.Category;

@Repository
public interface CategoryDb extends JpaRepository<Category, UUID>{
}
