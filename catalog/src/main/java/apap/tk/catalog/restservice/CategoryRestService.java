
package apap.tk.catalog.restservice;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.model.Category;
import apap.tk.catalog.repository.CatalogDb;
import apap.tk.catalog.repository.CategoryDb;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryRestService {
    @Autowired
    private CategoryDb categoryDb;

    public Category createRestCategory(Category category) { 
        return categoryDb.save(category);
    };

    public void addCategory(Category category){
        categoryDb.save(category);
    }

    public List<Category> getAllCategory(){
        return categoryDb.findAll();
    }

}
