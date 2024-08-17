package telran.currency;

import java.util.HashSet;
import java.util.List;

import telran.currency.service.CurrencyConvertor;
import telran.view.InputOutput;
import telran.view.Item;

public class CurrencyItems {
	private static final long MIN_NUMBERS = 1;
    private static final long MAX_NUMBERS = 30;
    private static final long MIN_AMOUNT = 1;
    private static final long MAX_AMOUNT = 1000000;
    private CurrencyConvertor currencyConvertor;
    

    public CurrencyItems(CurrencyConvertor currencyConvertor) {
        this.currencyConvertor = currencyConvertor;
    }

    public List<Item> getItems() {
        return List.of(
            Item.of("Strongest currencies", this::strongestCurrencies),
            Item.of("Weakest currencies", this::weakestCurrencies),
            Item.of("Convert", this::convert),
            Item.of("Get all codes", this::getAllCodes),
            Item.ofExit()
        );
    }

    public void strongestCurrencies(InputOutput io) {
        int numbersOfCurrencies = (int) io.readNumberRange("Enter number of currencies",
            "Wrong number of currencies. Must be from 1 to 30", MIN_NUMBERS, MAX_NUMBERS).longValue();
        List<String> result = currencyConvertor.strongestCurrencies(numbersOfCurrencies);
        io.writeString("Strongest currencies: " + result + "\n");
    }

    public void weakestCurrencies(InputOutput io) {
        int numbersOfCurrencies = (int) io.readNumberRange("Enter number of currencies",
            "Wrong number of currencies. Must be from 1 to 30", MIN_NUMBERS, MAX_NUMBERS).longValue();
        List<String> result = currencyConvertor.weakestCurrencies(numbersOfCurrencies);
        io.writeString("Weakest currencies: " + result + "\n");
    }

    public void convert(InputOutput io) {
        String codeFrom = io.readStringPredicate("Write the abbreviated name of the currency you want to exchange", 
            "Currency will not be found. Hint: the abbreviation should be three capital letters", str -> str.matches("[A-Z]{3}"));
        String codeTo = io.readStringPredicate("Specify the currency you want to receive", 
            "Currency will not be found. Hint: the abbreviation should be three capital letters", str -> str.matches("[A-Z]{3}"));
        int amount = (int) io.readNumberRange("Enter amount",
            "Wrong amount. Must be from 1 to 1 000 000", MIN_AMOUNT, MAX_AMOUNT).longValue();
        double result = currencyConvertor.convert(codeFrom, codeTo, amount);
        io.writeString("Converted amount: " + result + " " +codeTo.toString() +"\n" );
    }

    public void getAllCodes(InputOutput io) {
        HashSet<String> codes = currencyConvertor.getAllCodes();
        io.writeString("All currency codes: " + codes + "\n");
    }
}

