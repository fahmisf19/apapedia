package apap.tk.catalog.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import apap.tk.catalog.dto.CatalogMapper;
import apap.tk.catalog.dto.request.CreateCatalogRequestDTO;
import apap.tk.catalog.dto.request.UpdateCatalogDTO;
import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.restservice.CatalogRestService;
import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;

import java.io.IOException;
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

    @PutMapping(value = "/catalog/{idCatalog}/update")
    public ResponseEntity<Catalog> updateCatalog(@PathVariable("idCatalog") UUID idCatalog, @RequestBody UpdateCatalogDTO updateCatalogDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has an invalid type or missing field");
        } else {
            var existingCatalog = catalogRestService.getRestCatalogById(idCatalog);
            if (existingCatalog == null){
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Catalog not found with id: " + idCatalog
                );
            }
            var updateCatalog = catalogMapper.updateCatalogRequestDTOToCatalog(updateCatalogDTO); // Implement the mapping method.
            var catalog = catalogRestService.updateRestCatalog(idCatalog, updateCatalog); // Implement the update service method.
            return ResponseEntity.ok().body(catalog); // Return the updated catalog as JSON response.
        }
    }

    @DeleteMapping(value = "/catalog/{idCatalog}")
    public ResponseEntity<String> restDeleteCatalog(@PathVariable("idCatalog") UUID idCatalog) {
        // Cek apakah Catalog dengan ID yang sesuai ada dalam basis data
        var catalog = catalogRestService.getRestCatalogById(idCatalog);
        if (catalog == null) {
            // Jika Catalog tidak ditemukan, kembalikan respons NOT FOUND
            return ResponseEntity.notFound().build();
        }

        // Lakukan penghapusan Catalog
        catalogRestService.deleteCatalog(catalog);

        // Kembalikan respons OK jika penghapusan berhasil
        return ResponseEntity.ok("Catalog dengan id " + idCatalog + " berhasil dihapus");
    
    }
    
    // POST catalog
    @PostMapping(value = "/catalog/create")
    public ResponseEntity<Catalog> addCatalog(@RequestBody CreateCatalogRequestDTO createCatalogRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has an invalid type or missing field");
        } else {
            var catalog = catalogMapper.createCatalogRequestDTOToCatalog(createCatalogRequestDTO); // Implement the mapping method.
            catalog.setSeller(createCatalogRequestDTO.getSeller());
            catalog.setPrice(createCatalogRequestDTO.getPrice());
            catalog.setProductName(createCatalogRequestDTO.getProductName());
            catalog.setProductDescription(createCatalogRequestDTO.getProductDescription());
            catalog.setStock(createCatalogRequestDTO.getStock());
            catalog.setCategory(createCatalogRequestDTO.getCategory());
            catalog.setImage(createCatalogRequestDTO.getImage());
            catalogRestService.createRestCatalog(catalog);
            return new ResponseEntity<>(catalog, HttpStatus.CREATED);
        }
    }

    // GET Catalog by seller id
    @GetMapping(value = "/catalog/{sellerId}")
    private ResponseEntity<List<Catalog>> getCatalogsBySellerId(@PathVariable("sellerId") String sellerId) {
        try {
            List<Catalog> listCatalog = catalogRestService.retrieveListCatalogBySellerId(UUID.fromString(sellerId));
            if (listCatalog.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No items found for user with id: " + sellerId
                );
            }
            return new ResponseEntity<>(listCatalog, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
              HttpStatus.NOT_FOUND, "Id Seller " + sellerId + " tidak terdaftar"  
            );
        }
    }

    @GetMapping(value = "/catalog/get-all")
    public List<Catalog> getAllCatalogs() {
        return catalogRestService.getAllCatalog();
    }

     @GetMapping(value = "/catalog/sellerId/{sellerId}")
     private List<Catalog> retrieveListCatalog(@PathVariable("sellerId") String sellerId) {
         try {
             return catalogRestService.retrieveListCatalogBySellerId(UUID.fromString(sellerId));
         } catch (NoSuchElementException e) {
             throw new ResponseStatusException(
               HttpStatus.NOT_FOUND, "Id Seller " + sellerId + " tidak terdaftar"
             );
         }
     }

    @GetMapping(value = "/catalog/search-catalog-name")
    public List<Catalog> filterCatalogName(@RequestParam("catalog") String catalog) {
        try{
            List<Catalog> catalogs = catalogRestService.findCatalogByName(catalog);
            return catalogs;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "catalog's name not found");
        }
    }

    @GetMapping(value = "/catalog/search-catalog-name/{sellerId}")
    public List<Catalog> filterCatalogSellerAndName(@PathVariable("sellerId") UUID sellerId, @RequestParam("catalog") String catalog) {
        try{
            List<Catalog> catalogs = catalogRestService.findCatalogBySellerAndName(sellerId, catalog);
            return catalogs;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "catalog's name not found");
        }
    }

    @GetMapping(value = "/catalog/search-catalog-price")
    public List<Catalog> filterCatalogPrice(@RequestParam("lowerLimitPrice") Integer lowerLimitPrice, @RequestParam("higherLimitPrice") Integer higherLimitPrice) {
        try{
            List<Catalog> catalogs = catalogRestService.findCatalogByPrice(lowerLimitPrice, higherLimitPrice);
            return catalogs;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "catalog that's in range price between " + lowerLimitPrice + " - " + higherLimitPrice + " not found");
        }
    }


    // GET All Catalog (default by name ASC) 
    @GetMapping("catalog/getAll")
    public ResponseEntity<List<Catalog>> getListCatalog() {
        try {
            // Mendapatkan daftar katalog dari CatalogDb, diurutkan berdasarkan nama secara default
            List<Catalog> catalogList = catalogRestService.getAllCatalog();
            return new ResponseEntity<>(catalogList, HttpStatus.OK);
        } catch (Exception e) {
            // Tangani pengecualian jika terjadi kesalahan
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET Catalog by Catalog ID
    @GetMapping("catalog/getByCatalogId")
    public ResponseEntity<Catalog> getCatalogByCatalogId(@RequestParam UUID catalogId) {
        Catalog catalog = catalogRestService.getRestCatalogById(catalogId);
    
        if (catalog == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(catalog, HttpStatus.OK);
        }
    }

    // GET Catalog List Sort by Price or Name and Ascending or Descending Order
    @GetMapping("/getAllSorted")
    public ResponseEntity<List<Catalog>> getAllCatalogSorted(
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        try {
            // Mendapatkan daftar katalog yang diurutkan
            List<Catalog> catalogList = catalogRestService.getAllCatalogSorted(sortBy, sortOrder);

            // Mengembalikan daftar katalog dalam ResponseEntity
            return new ResponseEntity<>(catalogList, HttpStatus.OK);
        } catch (Exception e) {
            // Tangani pengecualian jika terjadi kesalahan
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  
}
