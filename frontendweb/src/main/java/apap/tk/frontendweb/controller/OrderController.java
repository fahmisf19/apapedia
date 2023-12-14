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
import apap.tk.frontendweb.security.jwt.JwtUtils;
import apap.tk.frontendweb.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("order/viewall")
    // public String getOrderHistory(Model model, @RequestParam("sellerId") UUID sellerId) throws IOException, InterruptedException {
    public String getOrderHistory(Model model, HttpServletRequest request) throws IOException, InterruptedException {
        HttpSession session = request.getSession(false);
        String jwtToken = null;
        if (session != null) jwtToken = (String) session.getAttribute("token");
        if (jwtToken != null && !jwtToken.isBlank()) {
            String userId = jwtUtils.getUserIdFromToken(jwtToken);
            UUID sellerID = UUID.fromString(userId);           
            List<ReadOrderResponseDTO> orderList = orderService.getListOrder(sellerID, jwtToken);
            model.addAttribute("orderList", orderList);
            model.addAttribute("sellerId", sellerID);
        }
        
        return "order/order-history";
    }

    @PostMapping("order/update-status/{orderId}/{newStatus}")
    public String updateOrderStatus(@PathVariable UUID orderId, @PathVariable Integer newStatus, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            String jwtToken = null;
            if (session != null) jwtToken = (String) session.getAttribute("token");
            if (jwtToken != null && !jwtToken.isBlank()) {
                orderService.updateOrderStatus(orderId, newStatus, jwtToken);
            }
            return "redirect:../../viewall";
        } catch (Exception e) {
            // Handle error jika diperlukan
            return "redirect:../../viewall?error=true";
        }
    }
    
}
