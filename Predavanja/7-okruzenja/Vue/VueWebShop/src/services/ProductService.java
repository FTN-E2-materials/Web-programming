package services;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.webshop.Product;
import beans.webshop.Products;
import beans.webshop.ShoppingCart;
import beans.webshop.ShoppingCartItem;
import beans.webshop.kendo.KendoProductList;
import beans.webshop.kendo.KendoShoppingCart;
import beans.webshop.kendo.ProductToAdd;

@Path("/proizvodi")
public class ProductService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Hello Jersey";
	}

	@GET
	@Path("/getProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public KendoProductList getProductList() {
		return getKendoProductList();
	}

	@GET
	@Path("/getJustProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Product> getJustProducts() {
		return getProducts().getValues();
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String add(ProductToAdd p) {
		getShoppingCart().addItem(getProducts().getProduct(p.id), p.count);
		System.out.println("Product " + getProducts().getProduct(p.id)
				+ " added with count: " + p.count);
		return "OK";
	}

	@GET
	@Path("/getSc")
	@Produces(MediaType.APPLICATION_JSON)
	public KendoShoppingCart getSc() {
		return getKendoShoppingCart();
	}

	@GET
	@Path("/getJustSc")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShoppingCartItem> getJustSc() {
		return getShoppingCart().getItems();
	}
	
	@GET
	@Path("/getTotal")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTotal() {
		return "" + getShoppingCart().getTotal();
	}

	@POST
	@Path("/clearSc")
	@Produces(MediaType.APPLICATION_JSON)
	public String clearSc() {
		getShoppingCart().getItems().clear();
		return "OK";
	}

	private Products getProducts() {
		Products products = (Products) ctx.getAttribute("products");
		if (products == null) {
			products = new Products();
			ctx.setAttribute("products", products);
		} 
		if (ctx.getAttribute("productList") == null) {
			ctx.setAttribute("productList", new KendoProductList(products));
		}
		return products;
	}
	
	private KendoProductList getKendoProductList() {
		getProducts();
		return (KendoProductList) ctx.getAttribute("productList");
	}
	
	private ShoppingCart getShoppingCart() {
		ShoppingCart sc = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
		if (sc == null) {
			sc = new ShoppingCart();
			request.getSession().setAttribute("shoppingCart", sc);
		} 
		if (request.getSession().getAttribute("kendoShoppingCart") == null) {
			request.getSession().setAttribute("kendoShoppingCart", new KendoShoppingCart(sc));
		}
		return sc;
	}
	
	private KendoShoppingCart getKendoShoppingCart() {
		getShoppingCart();
		return (KendoShoppingCart)request.getSession().getAttribute("kendoShoppingCart");
	}

}
