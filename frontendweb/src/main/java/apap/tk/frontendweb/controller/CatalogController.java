package apap.tk.frontendweb.controller;

import java.util.List;
import java.util.UUID;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import apap.tk.frontendweb.dto.response.catalog.CategoryDTO;
import apap.tk.frontendweb.service.CatalogService;
import apap.tk.frontendweb.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Transactional
public class CatalogController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    CatalogService catalogService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/catalog/create-catalog")
    public String showCatalogForm(Model model) {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
        List<CategoryDTO> categories = categoryService.getAllCategories();

        // Membuat objek CatalogDTO baru untuk form
        CatalogDTO catalogDTO = new CatalogDTO();
        
        // Menambahkan daftar kategori ke dalam model
        model.addAttribute("categories", categories);
        model.addAttribute("catalogDTO", catalogDTO);
        model.addAttribute("sellerId", sellerId);
        return "catalog/createCatalog";
    }

    @PostMapping("/add")
    public String addCatalog(@RequestParam("imageFile") MultipartFile imageFile, CatalogDTO catalogDTO, RedirectAttributes redirectAttributes) {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
        try {
            catalogDTO.setSeller(sellerId);

            // Mengambil byte[] dari MultipartFile
            byte[] imageBytes = imageFile.getBytes();

            catalogDTO.setImage(imageBytes);

            // Panggil layanan untuk menambahkan katalog
            catalogService.addCatalog(catalogDTO);

            // Tambahkan pesan sukses yang akan ditampilkan di halaman home
            redirectAttributes.addFlashAttribute("successMessage", "Catalog added successfully.");

            return "redirect:/";
        } catch (Exception e) {
            logger.error("Erro occured while adding catalog.", e);

            // Tambahkan pesan error yang akan ditampilkan di halaman error jika diperlukan
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred while adding catalog.");

            return "redirect:/error-page"; // Redirect ke halaman error atau penanganan yang sesuai
        }
    }

    @GetMapping("/catalog/update/{catalogId}")
    public String showEditCatalogForm(@PathVariable UUID catalogId, Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();

        var catalogDTO = catalogService.getCatalogByCatalogId(catalogId);
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
            }

            // Mendapatkan objek Category dari CatalogDTO
            var categoryDTO = catalogDTO.getCategory();

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

            return "redirect:/";
        } catch (Exception e) {
            logger.error("Erro occured while updating catalog.", e);
            return "redirect:/"; // Redirect to an error page or appropriate handling
        }
    }
}
