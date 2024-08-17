package telran.currency;

import telran.view.Item;
import telran.view.Menu;
import telran.view.SystemInputOutput;
import telran.currency.service.CurrencyConvertor;
import telran.currency.service.FixerApiPerDay;

public class CurrencyConvertorAppl {
	public static void main(String[] args) throws Exception {
		try {
          
            CurrencyConvertor convertor = new FixerApiPerDay();    
            CurrencyItems items = new CurrencyItems(convertor);
            Menu menu = new Menu("Currency exchange", items.getItems().toArray(Item[]::new));  
            menu.perform(new SystemInputOutput());
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}

