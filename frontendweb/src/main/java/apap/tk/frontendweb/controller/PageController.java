package apap.tk.frontendweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import apap.tk.frontendweb.restservice.UserRestService;
import apap.tk.frontendweb.security.xml.ServiceResponse;
import apap.tk.frontendweb.setting.Setting;

import java.security.Principal;

@Controller
public class PageController {
    @Autowired
    private UserRestService userRestService;

    private WebClient webClient = WebClient.builder()
            .codecs(configurer -> configurer.defaultCodecs()
                    .jaxb2Decoder(new Jaxb2XmlDecoder()))
            .build();


    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(
            @RequestParam(value = "ticket", required = false) String ticket,
            HttpServletRequest request
    ) {
        try {
                var serviceResponse = this.webClient.get().uri(
                        String.format(
                                Setting.SERVER_VALIDATE_TICKET,
                                ticket,
                                Setting.CLIENT_LOGIN
                        )
                ).retrieve().bodyToMono(ServiceResponse.class).block();

                String username = "";
                try {
                    username = serviceResponse != null ? serviceResponse.getAuthenticationSuccess().getUser() : null;
                } catch (NullPointerException e) {
                    return new ModelAndView("redirect:/user/add?error=1");
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, "SELLER", null);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
        
                var token = userRestService.getToken(username, "APAPEDIA");
        
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
                httpSession.setAttribute("token", token);
        
                return new ModelAndView("redirect:/");              
        } catch (Exception e) {
                return new ModelAndView("redirect:/user/add?error=1");
        }

    }

    @GetMapping("/login-sso")
    public ModelAndView loginSSO() {
        return new ModelAndView("redirect:" + Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

    @GetMapping("/logout-sso")
    public ModelAndView logoutSSO(Principal principal) {
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }
}
