package telran.currency.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

public class FixerApiPerDay extends AbstractCurrencyConvertor {
    private static final String URI_STRING = "http://data.fixer.io/api/latest?access_key=493e9e7a06eced2d550040cefc5ef719";
    private LocalDateTime lastUpdatedTime;

    public FixerApiPerDay() {
        rates = getRates();
    }

    public HashMap<String, Double> getRates() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI(URI_STRING)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String content = response.body();
            JSONObject jsonObject = new JSONObject(content);
            JSONObject ratesObject = jsonObject.getJSONObject("rates");
            rates.clear(); 
            for (String key : ratesObject.keySet()) {
                rates.put(key, ratesObject.getDouble(key));
            }
            lastUpdatedTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
            System.out.println("Data refreshed at: " + lastUpdatedTime);

        } catch (Exception e) {
            throw new RuntimeException("Error while refreshing rates: " + e.getMessage(), e);
        }
        return new HashMap<>(rates);
    }

    private void refresh() {
        if (lastUpdatedTime == null || Duration.between(lastUpdatedTime, LocalDateTime.now()).toHours() >= 24) {
            getRates();
        }
    }

  

    @Override
    public List<String> strongestCurrencies(int amount) {
    	refresh();
        return super.strongestCurrencies(amount);
    }

    @Override
    public List<String> weakestCurrencies(int amount) {
    	refresh();
        return super.weakestCurrencies(amount);
    }

    @Override
    public double convert(String codeFrom, String codeTo, int amount) {
    	refresh();
        return super.convert(codeFrom, codeTo, amount);
    }

    @Override
    public HashSet<String> getAllCodes() {
    	refresh();
        return super.getAllCodes();
    }
}