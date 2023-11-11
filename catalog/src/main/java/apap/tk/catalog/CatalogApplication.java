package apap.tk.catalog;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import jakarta.transaction.Transactional;

import com.github.javafaker.Faker;

import apap.tk.catalog.dto.CategoryMapper;
import apap.tk.catalog.dto.request.CreateCategoryRequestDTO;
import apap.tk.catalog.restservice.CategoryRestService;

import com.github.javafaker.Faker;

@SpringBootApplication
public class CatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(CategoryRestService categoryRestService, CategoryMapper categoryMapper) {
		return args -> {
			var faker = new Faker(new Locale("in-ID"));

			

			for (int i = 0; i < 1; i++) {
				
				var categoryDTO = new CreateCategoryRequestDTO();
				// buat dummy data karyawan
				categoryDTO.setName("Makanan");
				
				var category = categoryMapper.createCategoryRequestDTOToCategory(categoryDTO);
				category = categoryRestService.createRestCategory(category);

			}
		};
	}

}
