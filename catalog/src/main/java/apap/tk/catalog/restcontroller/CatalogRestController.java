package apap.tk.catalog.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import apap.tk.catalog.dto.CatalogMapper;
import apap.tk.catalog.dto.request.CreateCatalogRequestDTO;
import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.restservice.CatalogRestService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CatalogRestController {

    @Autowired
    private CatalogMapper catalogMapper;

    @Autowired
    private CatalogRestService catalogRestService;
    
    // POST catalog
    @PostMapping(value = "/catalog/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Catalog restAddCatalog(@Valid @RequestBody CreateCatalogRequestDTO catalogDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"  
            );
        } else {
            // if (image != null) {
            //     catalogDTO.setImage(image);
            // }

            var catalog = catalogMapper.CreateCatalogRequestDTOToCatalog(catalogDTO);
            catalogRestService.createRestCatalog(catalog);
            return catalog;
        }
    }

    // GET Catalog by seller id
    @GetMapping(value = "/catalog/{sellerId}")
    private List<Catalog> retrieveListCatalog(@PathVariable("sellerId") String sellerId) {
        try {
            return catalogRestService.retrieveListCatalogBySellerId(UUID.fromString(sellerId));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
              HttpStatus.NOT_FOUND, "Id Seller " + sellerId + " tidak terdaftar"  
            );
        }
    }
}
