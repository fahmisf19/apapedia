package apap.tk.frontendweb.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        public String addCatalog(@RequestParam("image") String imageBase64, CatalogDTO catalogDTO, RedirectAttributes redirectAttributes) {
            UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
            try {
                // Decode base64 string to byte array
                byte[] imageByteArray = Base64.getDecoder().decode(imageBase64);

                catalogDTO.setSeller(sellerId);

                // Create a CatalogDTO instance and set the image byte array
                catalogDTO.setImage(imageByteArray);

                // Call the service to add the catalog
                catalogService.addCatalog(catalogDTO);

                // Tambahkan pesan sukses yang akan ditampilkan di halaman home
                redirectAttributes.addFlashAttribute("successMessage", "Catalog added successfully.");

                return "redirect:/";
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception

                // Tambahkan pesan error yang akan ditampilkan di halaman error jika diperlukan
                redirectAttributes.addFlashAttribute("errorMessage", "Error occurred while adding catalog.");

                return "redirect:/error-page"; // Redirect to an error page or appropriate handling
            }
        }

}
