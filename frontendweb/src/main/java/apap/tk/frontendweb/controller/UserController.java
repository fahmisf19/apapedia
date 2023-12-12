package apap.tk.frontendweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import apap.tk.frontendweb.dto.auth.request.CreateUserRequestDTO;
import apap.tk.frontendweb.dto.auth.response.ReadUserResponseDTO;
import apap.tk.frontendweb.restservice.UserRestService;

@Controller
public class UserController {
    @Autowired
    UserRestService userRestService;

    @GetMapping("/user/add")
    public String formAddUser(Model model) {
        CreateUserRequestDTO userDTO = new CreateUserRequestDTO();
        model.addAttribute("userDTO", userDTO);

        return "user/form-add-user";
    }

    @PostMapping("/user/add")
    public String submitUser(@ModelAttribute CreateUserRequestDTO createUserRequestDTO, Model model, HttpServletRequest request) {
        createUserRequestDTO.setRole(2L);
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");
        ReadUserResponseDTO userResultDTO = userRestService.sendUser(createUserRequestDTO, jwtToken);

        if (userResultDTO.getId() == null) {
            return "user/error-add-user";
        }

        model.addAttribute("user", userResultDTO);
        return "user/success-add-user";
    }
}
