package apap.tk.catalog;

import java.math.BigInteger;
import java.util.Locale;
import java.util.UUID;

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

			for (int i = 1; i <= 13; i++) {
				var categoryDTO = new Category();
            	UUID categoryId;

				// Tentukan UUID kategori sesuai dengan nama kategori
				if (i == 1) {
					categoryId = UUID.fromString("43cfd2e4-7c72-4f3d-b6b8-d22a33a7eb69");
				} else if (i == 2) {
					categoryId = UUID.fromString("a3e1e722-e00e-41cd-86b1-97754500ab6b");
				} else if (i == 3) {
					categoryId = UUID.fromString("b48f3c1a-53e7-4f09-8f9e-e7b7d52e8a0b");
				} else if (i == 4) {
					categoryId = UUID.fromString("2b8ddc04-08d1-4e77-af15-0e71bf7bb1db");
				} else if (i == 5) {
					categoryId = UUID.fromString("cbe09de0-cd5e-4c6d-8207-810f47021bd1");
				} else if (i == 6) {
					categoryId = UUID.fromString("5b88d50d-4c71-4ee1-80ef-8242f9a1c080");
				} else if (i == 7) {
					categoryId = UUID.fromString("a99b1c14-bb3c-4e07-a150-0e2a4f2c50c4");
				} else if (i == 8) {
					categoryId = UUID.fromString("5c5a187a-7ebd-4bf8-b738-7e5a3312d3e7");
				} else if (i == 9) {
					categoryId = UUID.fromString("31dcb1e1-37e6-4e6e-9e08-0ff20493e915");
				} else if (i == 10) {
					categoryId = UUID.fromString("aa935890-9a04-4708-a2ad-5ea8b2999d7f");
				} else if (i == 11) {
					categoryId = UUID.fromString("4b33b07c-265c-4c76-95f3-d5237e1f4fb7");
				} else if (i == 12) {
					categoryId = UUID.fromString("b89e4f0a-5415-4215-9e63-b5619f6151a7");
				} else {
					categoryId = UUID.fromString("2ac1d232-0384-495c-97ec-846f3a1b6d9c");
				}

				categoryDTO.setIdCategory(categoryId);
           		categoryDTO.setName(getCategoryNameById(i));
				categoryRestService.addCategory(categoryDTO);
			}
			var catalogDTO = new Catalog();
			String fakeString = faker.lorem().sentence();

			catalogDTO.setPrice(BigInteger.valueOf(faker.number().randomDigitNotZero()));
			catalogDTO.setProductName(faker.commerce().productName());
			catalogDTO.setProductDescription(faker.lorem().sentence());
			catalogDTO.setStock(faker.number().numberBetween(1, 100));
			byte[] byteArray = null;
			// catalogDTO.setCategory(categoryDTO);
			catalogDTO.setImage(byteArray);
			catalogRestService.createRestCatalog(catalogDTO);
		};
	}

	private String getCategoryNameById(int categoryId) {
		switch (categoryId) {
			case 1: return "Aksesoris Fashion";
			case 2: return "Buku & Alat Tulis";
			case 3: return "Elektronik";
			case 4: return "Fashion Bayi & Anak";
			case 5: return "Fashion Muslim";
			case 6: return "Fotografi";
			case 7: return "Hobi & Koleksi";
			case 8: return "Jam Tangan";
			case 9: return "Perawatan & Kecantikan";
			case 10: return "Makanan & Minuman";
			case 11: return "Otomotif";
			case 12: return "Perlengkapan Rumah";
			case 13: return "Souvenir & Party Supplies";
			default: return "Unknown Category";
		}
	}

}
