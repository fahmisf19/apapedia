package apap.tk.frontendweb.controller;

import java.io.IOException;
import java.util.List;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import apap.tk.frontendweb.dto.response.catalog.CategoryDTO;
import apap.tk.frontendweb.service.CatalogService;
import apap.tk.frontendweb.service.CategoryService;
import jakarta.transaction.Transactional;

@Controller
@Transactional
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/catalog/create-catalog")
    public String showCatalogForm(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();

        // Membuat objek CatalogDTO baru untuk form
        CatalogDTO catalogDTO = new CatalogDTO();
        
        // Menambahkan daftar kategori ke dalam model
        model.addAttribute("categories", categories);
        model.addAttribute("catalogDTO", catalogDTO);
        return "catalog/createCatalog";
    }

    @PostMapping("/add")
    public String addCatalog(@RequestParam("image") String imageBase64, CatalogDTO catalogDTO) {
        try {
            // Decode base64 string to byte array
            byte[] imageByteArray = Base64.getDecoder().decode(imageBase64);

            // Create a CatalogDTO instance and set the image byte array
            catalogDTO.setImage(imageByteArray);
            System.out.println("bytenya:"+imageByteArray);
            // Call the service to add the catalog
            catalogService.addCatalog(catalogDTO);

            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception
            return "error-page"; // Redirect to an error page or appropriate handling
        }
    }
}
