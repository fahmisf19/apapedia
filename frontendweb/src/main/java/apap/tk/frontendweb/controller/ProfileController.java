package apap.tk.frontendweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import apap.tk.frontendweb.dto.response.catalog.CatalogDTO;
import apap.tk.frontendweb.dto.response.catalog.CategoryDTO;

@Controller
public class ProfileController {
    @GetMapping("/profile/withdraw")
    public String showWithdrawForm(Model model) {
       
        return "profile/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdrawBalanceUser() {
        // 1. dapetin usernya dulu
        // 2. terus dapetin balance usernya berapa
        // 3. terus dicek input an usernya mencukupi balance nya ga, kalo cukup kurangin balancenya, kalo ga cukup kasi feedback aja

        return "profile";
    }
}
