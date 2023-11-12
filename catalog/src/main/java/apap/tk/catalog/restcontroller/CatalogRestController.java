package apap.tk.catalog.restcontroller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apap.tk.catalog.dto.CatalogMapper;
import apap.tk.catalog.dto.request.UpdateCatalogDTO;
import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.restservice.CatalogRestService;

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
}
