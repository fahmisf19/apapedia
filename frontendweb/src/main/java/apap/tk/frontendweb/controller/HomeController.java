package apap.tk.frontendweb.controller;

import apap.tk.frontendweb.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.UUID;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;
    @GetMapping("/")
    public String home(Model model) {
        UUID sellerId = UUID.fromString("eb385f70-862b-479b-b2e2-933d471c5a4e");
        var salesPerDay = homeService.getChartSales(sellerId);
        model.addAttribute("sellerId", sellerId);
        model.addAttribute("salesPerDay", salesPerDay);
        return "home/home";
    }
}
