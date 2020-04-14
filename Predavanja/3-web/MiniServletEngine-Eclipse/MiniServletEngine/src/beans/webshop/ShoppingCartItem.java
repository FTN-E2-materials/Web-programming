package beans.webshop;

/** Reprezentuje stavku u korpi. Stavku cine proizvod i kolicina. */
public class ShoppingCartItem {

  private Product product;
  public void setProduct(Product p) {
    product = p;
  }
  public Product getProduct() {
    return product;
  }

  private int count;
  public void setCount(int c) {
    count = c;
  }
  public int getCount() {
    return count;
  }

  public ShoppingCartItem(Product p, int count) {
    this.product = p;
    this.count = count;
  }
}