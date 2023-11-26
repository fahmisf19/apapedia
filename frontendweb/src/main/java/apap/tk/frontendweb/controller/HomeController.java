package apap.tk.frontendweb.controller;

import apap.tk.frontendweb.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    HomeService homeService;
    @GetMapping("/")
    public String home(Model model) {
//        var salesPerDay = homeService.getChartSales();
//        model.addAttribute(salesPerDay);
        return "home/home";
    }
}
