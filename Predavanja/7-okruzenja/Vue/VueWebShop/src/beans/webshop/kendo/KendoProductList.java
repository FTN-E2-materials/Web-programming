package beans.webshop.kendo;

import java.util.ArrayList;

import beans.webshop.Product;
import beans.webshop.Products;

public class KendoProductList {
	public ArrayList<Product> list;
	public KendoProductList() {
		list = new ArrayList<Product>();
	}
	public KendoProductList(Products products) {
		list = products.getProductList();
	}

}
