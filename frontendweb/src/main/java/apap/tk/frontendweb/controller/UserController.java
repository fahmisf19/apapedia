package apap.tk.frontendweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import apap.tk.frontendweb.dto.auth.request.CreateUserRequestDTO;
import apap.tk.frontendweb.dto.auth.response.ReadUserResponseDTO;
import apap.tk.frontendweb.restservice.UserRestService;

@Controller
public class UserController {
    @Autowired
    UserRestService userRestService;

    @GetMapping("/user/add")
    public String formAddUser(Model model) {
        var userDTO = new CreateUserRequestDTO();
        model.addAttribute("userDTO", userDTO);

        return "user/form-add-user";
    }

    @PostMapping("/user/add")
    public String submitUser(@ModelAttribute CreateUserRequestDTO createUserRequestDTO, Model model, @RequestParam(name = "error", required = false) String error) {
        createUserRequestDTO.setRole("SELLER");

        ReadUserResponseDTO userResultDTO = userRestService.createSeller(createUserRequestDTO);

        if (userResultDTO.getId() == null) {
            return "user/error-add-user";
        }

        model.addAttribute("user", userResultDTO);
        model.addAttribute("error", error);
        return "user/success-add-user";
    }
}
