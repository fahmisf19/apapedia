package apap.tk.frontendweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import apap.tk.frontendweb.dto.auth.request.UserRequestDTO;
import apap.tk.frontendweb.dto.auth.response.UserResponseDTO;
import apap.tk.frontendweb.restservice.UserRestService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRestService userRestService;

    @GetMapping("/user/auth")
    public String authenticationPage() {
        return "user/auth";
    }

    @GetMapping("/user/add")
    public String formAddUser(Model model) {
        UserRequestDTO userDTO = new UserRequestDTO();

        List<String> roles = new ArrayList<>();
        roles.add("Admin");
        roles.add("User");
        roles.add("Pustakawan");

        model.addAttribute("listCategory", roles);
        model.addAttribute("userDTO", userDTO);

        return "user/form-add-user";
    }

    @PostMapping("/user/add")
    public String submitUser(@ModelAttribute UserRequestDTO userDTO, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");
        UserResponseDTO userResultDTO = userRestService.sendUser(userDTO, jwtToken);

        if (userResultDTO.getId() == null) {
            return "user/error-add-user";
        }

        model.addAttribute("user", userResultDTO);
        return "user/success-add-user";
    }
}
