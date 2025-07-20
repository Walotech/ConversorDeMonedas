import java.util.Map;

public class ExchangeRateResponse {

    private String base_code;
    private Map<String, Double> conversion_rates; // Gson mapeará los pares clave-valor (moneda:tasa) aquí

    public String getBaseCode() {
        return base_code;
    }

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }

}


