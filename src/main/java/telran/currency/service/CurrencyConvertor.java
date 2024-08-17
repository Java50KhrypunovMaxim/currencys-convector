package telran.currency.service;

import java.util.HashSet;
import java.util.List;

public interface CurrencyConvertor {
	List<String> strongestCurrencies(int amount);
	List<String> weakestCurrencies(int amount);
	double convert(String codeFrom, String codeTo, int amount);
	HashSet<String> getAllCodes();
}