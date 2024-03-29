package beans.webshop;

/** Reprezentuje jedan proizvod. Cuva se id, naziv i jedinicna cena. */
public class Product {

	private String id;

	public void setId(String i) {
		id = i;
	}

	public String getId() {
		return id;
	}

	private String name;

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	private double price;

	public void setPrice(double p) {
		price = p;
	}

	public double getPrice() {
		return price;
	}

	public Product(String id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
}