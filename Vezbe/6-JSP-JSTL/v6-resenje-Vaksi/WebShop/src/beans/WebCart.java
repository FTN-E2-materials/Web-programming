package beans;

import java.util.Collection;
import java.util.HashMap;

/**
 * Klasa koja reprezentuje nasa virtualna kolica.
 * @author Vaxi
 *
 */
public class WebCart {
	
	private HashMap<String, Product> korpa;

	public WebCart() {
		korpa = new HashMap<String, Product>();
	}
	
	/**
	 * Metoda za dodavanje proizvoda u korpu.
	 * @param proizvod
	 * @return uspesnost dodavanja proizvoda u korpu
	 */
	public boolean dodajUKorpu(Product proizvod) {
		if (proizvod != null && !korpa.containsKey(proizvod.getId())) {
			korpa.put(proizvod.getId(), proizvod);
			return true;
		}

		return false;
	}

	public Collection<Product> getProducts() {
		return korpa.values();
	}
	
}
