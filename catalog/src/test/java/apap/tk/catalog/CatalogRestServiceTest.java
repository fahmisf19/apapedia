package apap.tk.catalog;

import apap.tk.catalog.model.Catalog;
import apap.tk.catalog.model.Category;
import apap.tk.catalog.repository.CatalogDb;
import apap.tk.catalog.repository.CategoryDb;
import apap.tk.catalog.restservice.CatalogRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CatalogRestServiceTest {

    @Mock
    private CatalogDb catalogDb;

    @Mock
    private CategoryDb categoryDb;

    @InjectMocks
    private CatalogRestService catalogRestService;

    private UUID catalogId;
    private Catalog mockCatalog;

    @BeforeEach
    void setUp() {
        catalogId = UUID.randomUUID();

        mockCatalog = new Catalog();
        mockCatalog.setId(catalogId);
        mockCatalog.setProductName("Sample Product");
        mockCatalog.setPrice(BigInteger.valueOf(100));
        mockCatalog.setProductDescription("Sample Description");
        mockCatalog.setStock(10);

        // Mock behavior untuk beberapa panggilan yang sering digunakan
        // seperti ketika memanggil catalogDb.findById() dengan catalogId, return mockCatalog
        when(catalogDb.findById(catalogId)).thenReturn(Optional.of(mockCatalog));

        // seperti ketika memanggil catalogDb.findAllByOrderByProductName(), return daftar beberapa katalog
        List<Catalog> mockCatalogs = new ArrayList<>();
        mockCatalogs.add(mockCatalog);
        mockCatalogs.add(new Catalog()); // Tambahkan lebih banyak jika perlu
        when(catalogDb.findAllByOrderByProductName()).thenReturn(mockCatalogs);
    }

    @Test
    void testGetAllCatalog() {
        List<Catalog> mockCatalogs = new ArrayList<>();
        mockCatalogs.add(new Catalog());
        mockCatalogs.add(new Catalog());
        when(catalogDb.findAllByOrderByProductName()).thenReturn(mockCatalogs);

        List<Catalog> result = catalogRestService.getAllCatalog();

        assertEquals(2, result.size());
    }

    @Test
    void testGetRestCatalogById() {
        UUID catalogId = UUID.randomUUID();
        Catalog mockCatalog = new Catalog();
        mockCatalog.setId(catalogId);
        when(catalogDb.findById(any(UUID.class))).thenReturn(Optional.of(mockCatalog));

        Catalog result = catalogRestService.getRestCatalogById(catalogId);

        assertEquals(catalogId, result.getId());
    }

    @Test
    void testUpdateRestCatalog() {
        UUID catalogId = UUID.randomUUID();
        Catalog existingCatalog = new Catalog();
        existingCatalog.setId(catalogId);

        Catalog updatedCatalog = new Catalog();
        updatedCatalog.setId(catalogId);
        updatedCatalog.setProductName("Updated Product");

        when(catalogDb.findById(any(UUID.class))).thenReturn(Optional.of(existingCatalog));
        when(catalogDb.save(any(Catalog.class))).thenReturn(updatedCatalog);

        Catalog result = catalogRestService.updateRestCatalog(catalogId, updatedCatalog);

        assertEquals(updatedCatalog.getProductName(), result.getProductName());
    }

    @Test
    void testDeleteCatalog() {
        UUID catalogId = UUID.randomUUID();
        Catalog catalogToDelete = new Catalog();
        catalogToDelete.setId(catalogId);

        catalogRestService.deleteCatalog(catalogToDelete);
        
        verify(catalogDb, times(1)).save(catalogToDelete);

    }
}
