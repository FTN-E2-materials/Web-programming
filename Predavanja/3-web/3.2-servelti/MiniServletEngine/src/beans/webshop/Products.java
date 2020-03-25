package beans.webshop;

import java.io.*;
import java.util.*;

/**
 * Klasa koja reprezentuje spisak raspolozivih proizvoda za kupovinu. Spisak
 * proizvoda se inicijalizuje iz tekstualne datoteke, koja je oblika: <br>
 * id;naziv;jedinicna cena
 */
public class Products {
	private HashMap<String, Product> products = new HashMap<String, Product>();

	public Products() {
		BufferedReader in = null;
		try {
			File file = new File("products.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readProducts(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cita proizvode iz datoteke i smesta ih u asocijativnu listu proizvoda.
	 * Kljuc je id proizvoda.
	 */
	private void readProducts(BufferedReader in) {
		String line, id = "", name = "", price = "";
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				String[] tokens = line.split(";");
				id = tokens[0].trim();
				name = tokens[1].trim();
				price = tokens[2].trim();
				products.put(id,
						new Product(id, name, Double.parseDouble(price)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Vraca broj proizvoda. */
	public int getProductCount() {
		return products.size();
	}

	/** Vraca enumerator na proizvode. */
	public Collection<Product> values() {
		return products.values();
	}

	/** Vraca proizvod na osnovu njegovog id-a. */
	public Product getProduct(String id) {
		return products.get(id);
	}
}
