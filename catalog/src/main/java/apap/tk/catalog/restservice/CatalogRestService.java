package apap.tk.catalog.restservice;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.repository.CatalogDb;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CatalogRestService {
    @Autowired
    private CatalogDb catalogDb;

    public void createRestCatalog(Catalog catalog) { 
        catalogDb.save(catalog); 
    };

}
