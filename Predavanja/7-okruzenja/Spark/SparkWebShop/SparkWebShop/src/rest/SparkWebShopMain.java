package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;

import com.google.gson.Gson;

import beans.webshop.ProductToAdd;
import beans.webshop.Products;
import beans.webshop.ShoppingCart;
import spark.Request;
import spark.Session;

public class SparkWebShopMain {

	private static Products products = new Products();
	private static Gson g = new Gson();


	public static void main(String[] args) throws Exception {
		port(8080);
		
		staticFiles.externalLocation(new File("./static").getCanonicalPath()); 
		
		get("/test", (req, res) -> {
			return "Works";
		});
		
		get("/rest/proizvodi/getJustProducts", (req, res) -> {
			res.type("application/json");
			return g.toJson(products.values());
		});
		
		get("/rest/proizvodi/getJustSc", (req, res) -> {
			res.type("application/json");
			return g.toJson(getSc(req).getItems());
		});
		
		get("/rest/proizvodi/getTotal", (req, res) -> {
			res.type("application/json");
			return g.toJson(getSc(req).getTotal());
		});
		
		post("/rest/proizvodi/add", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			ProductToAdd pd = g.fromJson(payload, ProductToAdd.class);
			getSc(req).addItem(products.getProduct(pd.id), pd.count);
			return ("OK");
		});
		
		post("/rest/proizvodi/clearSc", (req, res) -> {
			res.type("application/json");
			getSc(req).getItems().clear();
			return "OK";
		});
	}
	
	private static ShoppingCart getSc(Request req) {
		Session ss = req.session(true);
		ShoppingCart sc = ss.attribute("sc"); 
		if (sc == null) {
			sc = new ShoppingCart();
			ss.attribute("sc", sc);
		}
		return sc;
	}

}
