package telran.currency.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AbstractCurrencyConvertor implements CurrencyConvertor {
protected Map<String, Double> rates; 
String filePathStr;

	public AbstractCurrencyConvertor() {
	
	 rates = new HashMap<>();
}

	@Override
	public List<String> strongestCurrencies(int amount) {
		
		return rates.entrySet().stream().sorted(
				Map.Entry.<String,Double>comparingByValue()).limit(amount)
                .map(Map.Entry::getKey)
                .toList();
    }

	@Override
	public List<String> weakestCurrencies(int amount) {
		return rates.entrySet().stream().sorted(
				Map.Entry.<String,Double>comparingByValue().reversed()).limit(amount)
                .map(Map.Entry::getKey)
                .toList();
	}

	@Override
	public double convert(String codeFrom, String codeTo, int amount) {
        Double rateFrom = rates.get(codeFrom);
        Double rateTo = rates.get(codeTo);
        if (rateFrom == null || rateTo == null) {
            throw new IllegalArgumentException("Invalid currency code: " + codeFrom + " or " + codeTo);
        }
        return amount * (rateTo / rateFrom);
	}

	@Override
	public HashSet<String> getAllCodes() {
		return new HashSet<>(rates.keySet());
	}

}
