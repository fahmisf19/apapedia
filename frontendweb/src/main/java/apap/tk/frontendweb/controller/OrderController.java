package apap.tk.frontendweb.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import apap.tk.frontendweb.dto.response.order.ReadOrderResponseDTO;
import apap.tk.frontendweb.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("order/viewall")
    // public String getOrderHistory(Model model, @RequestParam("sellerId") UUID sellerId) throws IOException, InterruptedException {
    public String getOrderHistory(Model model) throws IOException, InterruptedException {
        String ID = "eb385f70-862b-479b-b2e2-933d471c5a4e";
        UUID sellerID = UUID.fromString(ID);
        List<ReadOrderResponseDTO> orderList = orderService.getListOrder(sellerID);
        model.addAttribute("orderList", orderList);
        return "order/order-history";
    }

    @PostMapping("order/update-status/{orderId}/{newStatus}")
    public String updateOrderStatus(@PathVariable UUID orderId, @PathVariable Integer newStatus) {
        try {
            orderService.updateOrderStatus(orderId, newStatus);
            // Lakukan hal lain yang diperlukan setelah update status
            return "redirect:../../viewall";
        } catch (Exception e) {
            // Handle error jika diperlukan
            return "redirect:../../viewall?error=true";
        }
    }
    
}
