package apap.tk.frontendweb.controller;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import apap.tk.frontendweb.security.jwt.JwtUtils;
import apap.tk.frontendweb.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;

    @Autowired
    private JwtUtils jwtUtils;

    private final UUID id = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
    
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String jwtToken = null;
        if (session != null) jwtToken = (String) session.getAttribute("token");
        if (jwtToken != null && !jwtToken.isBlank()) {
            String userId = jwtUtils.getUserIdFromToken(jwtToken);
            var sellerID = UUID.fromString(userId);
            model.addAttribute("sellerId", sellerID);
        }
        UUID sellerId = id;

        if (sellerId != null) {
             try {
                var quantityPerDay = homeService.getChartSales(sellerId);
                var catalogList = homeService.getCatalogBySellerId(sellerId);
                var imageBase64List = homeService.getImage(catalogList);

                model.addAttribute("imageBase64List", imageBase64List);
                model.addAttribute("sellerId", sellerId);
                model.addAttribute("quantityPerDay", quantityPerDay);
                model.addAttribute("catalogList", catalogList);

             } catch (Exception e) {
                 // Handle any exceptions that may occur when retrieving data
                 e.printStackTrace(); // Log the exception or handle it as needed
                 model.addAttribute("errorMessage", "Error occurred while loading data.");
             }
        } else {
            try {
                var catalogList = homeService.getAllCatalog();
                var imageBase64 = homeService.getImage(catalogList);

                model.addAttribute("imageBase64", imageBase64);
                model.addAttribute("catalogList", catalogList);
            } catch (Exception e) {
                // Handle any exceptions that may occur when retrieving data
                e.printStackTrace(); // Log the exception or handle it as needed
                model.addAttribute("errorMessage", "Error occurred while loading data.");
            }
        }

        return "home/home";
    }
    

    @GetMapping("/search-catalog-name")
    public String filterCatalogName(@RequestParam("catalog") String name, Model model) {
        UUID sellerId = id;

        if (sellerId != null) {
            var quantityPerDay = homeService.getChartSales(sellerId);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("quantityPerDay", quantityPerDay);

            var catalogList = homeService.searchCatalogSeller(sellerId, name);
            var imageBase64 = homeService.getImage(catalogList);

            model.addAttribute("imageBase64", imageBase64);
            model.addAttribute("catalogList", catalogList);
        } else {
            var catalogList = homeService.searchCatalog(name);
            var imageBase64 = homeService.getImage(catalogList);
            model.addAttribute("imageBase64", imageBase64);
            model.addAttribute("catalogList", catalogList);
        }

        return "home/home";
    }

    @GetMapping("/search-catalog-price")
    public String filterCatalogPrice(@RequestParam("lowerLimitPrice") Integer lowerLimitPrice,
                                     @RequestParam("higherLimitPrice") Integer higherLimitPrice,
                                     Model model) {
        UUID sellerId = id;

        if (sellerId != null) {
            var quantityPerDay = homeService.getChartSales(sellerId);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("quantityPerDay", quantityPerDay);

            var catalogList = homeService.searchCatalogPriceSeller(sellerId, lowerLimitPrice, higherLimitPrice);
            var imageBase64 = homeService.getImage(catalogList);

            model.addAttribute("imageBase64", imageBase64);
            model.addAttribute("catalogList", catalogList);
        } else {
            List<CatalogDTO> catalogList = homeService.searchCatalogPrice(lowerLimitPrice, higherLimitPrice);
            model.addAttribute("catalogList", catalogList);
        }
        return "home/home";
    }
    @GetMapping("/get-catalog")
    public String filterCatalogPrice(@RequestParam("sort") String sort, Model model) {
        UUID sellerId = id;

        String[] parts = sort.split("-");
        String sortBy = parts[0];
        String sortOrder = parts[1];

        if (sellerId != null) {
            var quantityPerDay = homeService.getChartSales(sellerId);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("quantityPerDay", quantityPerDay);

            List<CatalogDTO> catalogList = homeService.getSortedCatalogListSeller(sellerId, sortBy, sortOrder);
            var imageBase64 = homeService.getImage(catalogList);

            model.addAttribute("imageBase64", imageBase64);
            model.addAttribute("catalogList", catalogList);
        } else {
            List<CatalogDTO> catalogList = homeService.getSortedCatalogList(sortBy, sortOrder);
            var imageBase64 = homeService.getImage(catalogList);

            model.addAttribute("imageBase64", imageBase64);
            model.addAttribute("catalogList", catalogList);
        }
        return "home/home";
    }
}
