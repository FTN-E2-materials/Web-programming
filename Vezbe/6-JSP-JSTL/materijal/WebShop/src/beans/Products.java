package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Klasa koja reprezentuje spisak raspolozivih proizvoda za kupovinu. Spisak
 * proizvoda se inicijalizuje iz tekstualne datoteke, koja je oblika: <br>
 * id;naziv;jedinicna cena
 */
public class Products {
	
	private HashMap<String, Product> products = new HashMap<String, Product>();
	private static final String WEB_CONTENT_PATH = "..\\WebShop\\WebContent"; //TODO: Zamenite Vasom putanjom
	
	public Products() {
		this(WEB_CONTENT_PATH);
	}
	
	public Products(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/products.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readProducts(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}

	/**
	 * Cita proizvode iz datoteke i smesta ih u asocijativnu listu proizvoda.
	 * Kljuc je id proizvoda.
	 */
	private void readProducts(BufferedReader in) {
		String line, id = "", name = "", price = "";
		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					name = st.nextToken().trim();
					price = st.nextToken().trim();
				}
				products.put(id, new Product(id, name, Double
						.parseDouble(price)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Vraca kolekciju proizvoda. */
	public Collection<Product> values() {
		return products.values();
	}

	/** Vraca kolekciju proizvoda. */
	public Collection<Product> getValues() {
		return products.values();
	}

	/** Vraca proizvod na osnovu njegovog id-a. */
	public Product getProduct(String id) {
		return products.get(id);
	}
}
