package apap.tk.frontendweb.controller;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import apap.tk.frontendweb.service.CatalogService;
import apap.tk.frontendweb.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.util.Map;
=======
import java.util.List;
>>>>>>> 8e6ffbc5ea64de524207d46666efe294ead056b4
import java.util.UUID;
>>>>>>> 62692d7487122d1afddb2a42acc4852b1e312052

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;

    @Autowired
    CatalogService catalogService;
    @GetMapping("/")
    public String home(Model model) {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");

//        sellerId = null;

        if (sellerId != null) {
            var quantityPerDay = homeService.getChartSales(sellerId);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("quantityPerDay", quantityPerDay);

            List<CatalogDTO> catalogList = catalogService.getCatalogBySellerId(sellerId);
            model.addAttribute("catalogList", catalogList);
        } else {
            List<CatalogDTO> catalogList = catalogService.getAllCatalog();
            model.addAttribute("catalogList", catalogList);
        }
        return "home/home";
    }

    @GetMapping("/search-catalog-name")
    public String filterCatalogName(@RequestParam("catalog") String name, Model model) {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
//        sellerId = null;

        if (sellerId != null) {
            var quantityPerDay = homeService.getChartSales(sellerId);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("quantityPerDay", quantityPerDay);

            List<CatalogDTO> catalogList = catalogService.searchCatalogSeller(sellerId, name);
            model.addAttribute("catalogList", catalogList);
        } else {
            List<CatalogDTO> catalogList = catalogService.searchCatalog(name);
            model.addAttribute("catalogList", catalogList);
        }


        return "home/home";
    }

    @GetMapping("/search-catalog-price")
    public String filterCatalogPrice(@RequestParam("lowerLimitPrice") Integer lowerLimitPrice,
                                     @RequestParam("higherLimitPrice") Integer higherLimitPrice,
                                     Model model) {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
//        sellerId = null;

        if (sellerId != null) {
            var quantityPerDay = homeService.getChartSales(sellerId);
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("quantityPerDay", quantityPerDay);

            List<CatalogDTO> catalogList = catalogService.searchCatalogPriceSeller(sellerId, lowerLimitPrice, higherLimitPrice);
            model.addAttribute("catalogList", catalogList);
        } else {
            List<CatalogDTO> catalogList = catalogService.searchCatalogPrice(lowerLimitPrice, higherLimitPrice);
            model.addAttribute("catalogList", catalogList);
        }


        return "home/home";
    }
}
