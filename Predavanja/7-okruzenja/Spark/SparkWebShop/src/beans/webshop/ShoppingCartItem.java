package beans.webshop;

/** Reprezentuje stavku u korpi. Stavku cine proizvod i kolicina. */
public class ShoppingCartItem {

	private Product product;
	private int count;
	private double total;

	public ShoppingCartItem(Product p, int count) {
		this.product = p;
		this.count = count;
		calcTotal();
	}
	
	private void calcTotal() {
		this.total = this.product.getPrice() * this.count;
	}


	public void setProduct(Product p) {
		this.product = p;
		calcTotal();
	}

	public Product getProduct() {
		return product;
	}

	public void setCount(int c) {
		this.count = c;
		calcTotal();
	}

	public int getCount() {
		return count;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
}
