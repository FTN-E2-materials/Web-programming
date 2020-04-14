package beans.webshop;

/** Reprezentuje stavku u korpi. Stavku cine proizvod i kolicina. */
public class ShoppingCartItem {

	private Product product;
	private int count;

	public ShoppingCartItem(Product p, int count) {
		this.product = p;
		this.count = count;
	}
	
	
	public void setProduct(Product p) {
		product = p;
	}

	public Product getProduct() {
		return product;
	}

	public void setCount(int c) {
		count = c;
	}

	public int getCount() {
		return count;
	}
	
	public double getTotal() {
		return product.getPrice() * count;
	}

}
