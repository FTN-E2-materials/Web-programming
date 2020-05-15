package rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

import java.io.File;

import com.google.gson.Gson;

import beans.Student;
import beans.User;
import spark.Session;
import ws.WsHandler;

public class SparkAppMain {

	private static Gson g = new Gson();

	public static void main(String[] args) throws Exception {
		port(8080);

		webSocket("/ws", WsHandler.class);

		staticFiles.externalLocation(new File("./static").getCanonicalPath());
		
		get("/rest/demo/test", (req, res) -> {
			return "Works";
		});
		
		get("/rest/demo/book/:isbn", (req, res) -> {
			String isbn = req.params("isbn");
			return "/rest/demo/book received PathParam 'isbn': " + isbn;
		});

		get("/rest/demo/books", (req, res) -> {
			String num = req.queryParams("num");
			return "/rest/demo/book received QueryParam 'num': " + num;
		});
		
		get("/rest/demo/testheader", (req, res) -> {
			String cookie = req.headers("Cookie");
			return "/rest/demo/testheader received HeaderParam 'Cookie': " + cookie;
		});
		
		get("/rest/demo/testcookie", (req, res) -> {
			String cookie = req.cookie("pera");
			if (cookie == null) {
				res.cookie("pera", "Perin kolacic");
				return "/rest/demo/testcookie <b>created</b> CookieParam 'pera': 'Perin kolacic'";  
			} else {
				return "/rest/demo/testcookie <i><u>received</u></i> CookieParam 'pera': " + cookie;
			}
		});

		post("/rest/demo/forma", (req, res) -> {
			res.type("application/json");
			String ime = req.queryParams("ime");
			String prezime = req.queryParams("prezime");
			Student s = new Student(ime, prezime, null);
			return g.toJson(s);
		});

		post("/rest/demo/testjson", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			Student s = g.fromJson(payload, Student.class);
			s.setIme(s.getIme() + "2");
			s.setPrezime(s.getPrezime() + "2");
			return g.toJson(s);
		});

		post("/rest/demo/login", (req, res) -> {
			res.type("application/json");
			String payload = req.body();
			User u = g.fromJson(payload, User.class);
			Session ss = req.session(true);
			User user = ss.attribute("user");
			if (user == null) {
				user = u;
				ss.attribute("user", user);
			}
			return g.toJson(user);
		});

		get("/rest/demo/testlogin", (req, res) -> {
			Session ss = req.session(true);
			User user = ss.attribute("user");
			
			if (user == null) {
				return "No user logged in.";  
			} else {
				return "User " + user + " logged in.";
			}
		});

		get("/rest/demo/logout", (req, res) -> {
			res.type("application/json");
			Session ss = req.session(true);
			User user = ss.attribute("user");
			
			if (user != null) {
				ss.invalidate();
			}
			return true;
		});
	}
}
