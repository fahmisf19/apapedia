package apap.tk.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import apap.tk.user.model.Role;
import apap.tk.user.restservice.UserRestService;
import apap.tk.user.service.RoleService;
import jakarta.transaction.Transactional;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(UserRestService userService, RoleService roleService) {
		try {
			var roleCustomer = new Role();
			roleCustomer.setRole("Customer");
			roleService.addRole(roleCustomer);
		 
			var roleSeller = new Role();
			roleSeller.setRole("Seller");
			roleService.addRole(roleSeller);
		   } catch (Exception ignored) {}
		return null;
	}

}
