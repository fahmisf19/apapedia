package apap.tk.frontendweb.controller;

import java.io.IOException;
import java.util.List;
import java.util.Base64;

// import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import apap.tk.frontendweb.dto.response.catalog.CategoryDTO;
import apap.tk.frontendweb.service.CatalogService;
import apap.tk.frontendweb.service.CategoryService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.util.StringUtils;

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

            return "catalog/createCatalog-success";
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception
            return "error-page"; // Redirect to an error page or appropriate handling
        }
    }

    @GetMapping("/catalog/update/{catalogId}")
    public String showEditCatalogForm(@PathVariable UUID catalogId, Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();

        CatalogDTO catalogDTO = catalogService.getCatalogByCatalogId(catalogId);
        catalogDTO.setId(catalogId);

        model.addAttribute("categories", categories);
        model.addAttribute("catalogDTO", catalogDTO);

        return "catalog/edit-catalog";
    }

    @PostMapping("/update")
    public String editCatalog(@RequestParam(value = "image", required = false) String imageBase64, @ModelAttribute("catalogDTO") CatalogDTO catalogDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                // Handle validation errors
                return "catalog/edit-catalog";
            }

            // Jika imageBase64 tidak null atau kosong, berarti ada pembaruan gambar
            if (StringUtils.hasText(imageBase64)) {
                byte[] imageByteArray = Base64.getDecoder().decode(imageBase64);
                catalogDTO.setImage(imageByteArray);
                System.out.println("bytenya: " + imageByteArray);
            }

            // Mendapatkan objek Category dari CatalogDTO
            CategoryDTO categoryDTO = catalogDTO.getCategory();

            List<CategoryDTO> categories = categoryService.getAllCategories();

            // Mendapatkan nama kategori yang sesuai dari categories
            String categoryName = categories.stream()
                    .filter(c -> c.getIdCategory().equals(categoryDTO.getIdCategory()))
                    .findFirst()
                    .map(CategoryDTO::getName)
                    .orElse(null);

            // Menetapkan nama kategori ke dalam categoryDTO
            categoryDTO.setName(categoryName);

            // Menetapkan kembali objek Category yang sudah diperbarui ke dalam CatalogDTO
            catalogDTO.setCategory(categoryDTO);

            // Call the service method to update the catalog
            catalogService.updateCatalog(catalogDTO);

            return "catalog/success-update-catalog";
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception
            return "error-page"; // Redirect to an error page or appropriate handling
        }
    }
}
