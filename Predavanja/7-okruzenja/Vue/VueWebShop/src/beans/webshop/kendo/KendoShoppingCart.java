package beans.webshop.kendo;

import java.util.ArrayList;

import beans.webshop.ShoppingCart;
import beans.webshop.ShoppingCartItem;

public class KendoShoppingCart {
	public ArrayList<ShoppingCartItem> list;

	public KendoShoppingCart() {
		list = new ArrayList<ShoppingCartItem>();
	}

	public KendoShoppingCart(ShoppingCart sc) {
		list = sc.getItems();
	}
}
