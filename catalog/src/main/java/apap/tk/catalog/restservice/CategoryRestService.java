
package apap.tk.catalog.restservice;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tk.catalog.model.Category;
import apap.tk.catalog.repository.CategoryDb;

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

    public Category getCategoryById(UUID categoryId){
        for(Category category : getAllCategory()){
            if(category.getIdCategory().equals(categoryId)){
                return category;
            }
        }

        return null;
    }

}
