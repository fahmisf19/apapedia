package apap.tk.catalog;

import java.math.BigInteger;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.javafaker.Faker;

import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.model.Category;
import apap.tk.catalog.restservice.CatalogRestService;
import apap.tk.catalog.restservice.CategoryRestService;
import apap.tk.catalog.dto.CategoryMapper;
import apap.tk.catalog.dto.request.CreateCategoryRequestDTO;
import jakarta.transaction.Transactional;



@SpringBootApplication
public class CatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(CategoryRestService categoryRestService, CatalogRestService catalogRestService) {
		return args -> {
			var faker = new Faker(new Locale("in-ID"));
			var categoryDTO = new Category();
			var catalogDTO = new Catalog();
			var fakeCategory = faker.name();
			String fakeString = faker.lorem().sentence();
			categoryDTO.setName(fakeCategory.name());
			categoryRestService.addCategory(categoryDTO);

			catalogDTO.setPrice(BigInteger.valueOf(faker.number().randomDigitNotZero()));
			catalogDTO.setProductName(faker.commerce().productName());
			catalogDTO.setProductDescription(faker.lorem().sentence());
			catalogDTO.setStock(faker.number().numberBetween(1, 100));
			byte[] byteArray = fakeString.getBytes();
			catalogDTO.setCategory(categoryDTO);
			catalogDTO.setImage(byteArray);
			catalogRestService.createRestCatalog(catalogDTO);
		};
	}

}
