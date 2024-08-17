package telran.currency.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

public class FixerApiPerDay extends AbstractCurrencyConvertor {
	protected String uriString = "http://data.fixer.io/api/latest?access_key=493e9e7a06eced2d550040cefc5ef719";
	private static final String FILE_NAME = "TodayRates.txt";

	public FixerApiPerDay() {
		this.filePathStr = FILE_NAME;
		refreshSafely();
		rates = getRates();
	}

	protected HashMap<String, Double> getRates() {
		HashMap<String, Double> newRates = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePathStr))) {
			StringBuilder contentBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				contentBuilder.append(line.trim());
			}
			String content = contentBuilder.toString();
			JSONObject jsonObject = new JSONObject(content);

			JSONObject ratesObject = jsonObject.getJSONObject("rates");
			for (String key : ratesObject.keySet()) {

				newRates.put(key, ratesObject.getDouble(key));
			}

			String date = jsonObject.getString("date");
			System.out.println("Date of rates: " + date);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newRates;
	}

	@Override
	public List<String> strongestCurrencies(int amount) {

		return super.strongestCurrencies(amount);
	}

	@Override
	public List<String> weakestCurrencies(int amount) {

		return super.weakestCurrencies(amount);
	}

	@Override
	public double convert(String codeFrom, String codeTo, int amount) {

		return super.convert(codeFrom, codeTo, amount);
	}

	private void refresh() throws Exception {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(new URI(uriString)).build();
		HttpResponse<Path> response = httpClient.send(request, BodyHandlers.ofFile(Path.of(FILE_NAME)));
	}

	private void refreshSafely() {
		try {
			refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}