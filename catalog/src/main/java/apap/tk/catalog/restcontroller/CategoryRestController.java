package apap.tk.catalog.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apap.tk.catalog.model.Category;
import apap.tk.catalog.restservice.CategoryRestService;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryRestController {
    @Autowired
    CategoryRestService categoryRestService;

    @GetMapping(value = "/category/all-categories")
    public List<Category> getAllCategory(){
        try{
            return categoryRestService.getAllCategory();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Category not found");
        }
    }
}
