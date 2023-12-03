package apap.tk.frontendweb.service;

import java.util.Map;
import java.util.UUID;

public interface HomeService {
    Map<Integer, Long> getChartSales(UUID sellerId);
}
