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
			var roleUser = new Role();
			roleUser.setRole("User");
			roleService.addRole(roleUser);
		 
			var roleAdmin = new Role();
			roleAdmin.setRole("Admin");
			roleService.addRole(roleAdmin);
		 
			var rolePustakawan = new Role();
			rolePustakawan.setRole("Pustakawan");
			roleService.addRole(rolePustakawan);
		   } catch (Exception ignored) {}
		return null;
	}

}
