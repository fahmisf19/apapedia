package apap.tk.frontendweb.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import apap.tk.frontendweb.dto.response.catalog.CategoryDTO;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8081/api/category/all-categories";

    public CategoryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        ResponseEntity<List<CategoryDTO>> response = restTemplate.exchange(
            BASE_URL,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<CategoryDTO>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve categories");
        }
    }
}
