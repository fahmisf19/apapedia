package apap.tk.catalog.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tk.catalog.model.Category;
import apap.tk.catalog.repository.CategoryDb;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryRestService {
    @Autowired
    private CategoryDb categoryDb;

    public void addCategory(Category category){
        categoryDb.save(category);
    }

    public List<Category> getAllCategory(){
        return categoryDb.findAll();
    }
}
