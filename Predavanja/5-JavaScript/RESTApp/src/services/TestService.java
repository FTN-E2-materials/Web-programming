package services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

import beans.Student;
import beans.User;

@Path("/demo")
public class TestService {

	/**
	 * Testira REST sistem. URL izgleda ovako: <code>rest/demo/test</code>
	 * 
	 * @return Vraæa tekst da vidimo da sve radi.
	 */
	@GET
	@Path("/test")
	public String test() {
		return "REST is working.";
	}

	/**
	 * Testira PathParam. URL izgleda ovako:
	 * <code>rest/demo/book/101-456-890</code>
	 * 
	 * @param isbn
	 *            PathParam tip parametra, odn. deo putanje.
	 * @return Vraæa odštampane primljene parametre.
	 */
	@GET
	@Path("/book/{isbn}")
	public String getBook(@PathParam("isbn") String isbn) {
		return "/rest/demo/book received PathParam 'isbn': " + isbn;
	}

	/**
	 * Testira PathParam sa PathSegment tipom parametra. URL izgleda ovako:
	 * <code>rest/demo/booksegment/101-456-890;name=Sabrana dela;author=Pera Periæ</code>
	 * 
	 * @param id
	 *            PathParam tip parametra, ali nije string, nego mapa
	 *            parametara.
	 * @return Vraæa odštampane primljene parametre.
	 */
	@GET
	@Path("/booksegment/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getBook(@PathParam("id") PathSegment id) {
		String retVal = "/rest/demo/booksegment received PathParam 'id', with PathSegment 'id'\n" + "path: "
				+ id.getPath() + "\n" + "segmentMap: " + id.getMatrixParameters() + "\n" + "segment.name: "
				+ id.getMatrixParameters().get("name");
		return retVal;
	}

	/**
	 * Testira QueryParam tip parametra. URL izgleda ovako:
	 * <code>rest/demo/books?num=12345</code>
	 * 
	 * @param num
	 *            QueryParam tip parametra; ovakav parametar je uobièajen za
	 *            forme.
	 * @return Vraæa odštampane primljene parametre.
	 */
	@GET
	@Path("/books")
	public String getBooks(@QueryParam("num") int num) {
		return "/rest/demo/books received QueryParam 'num': " + num;
	}

	/**
	 * Testira HeaderParam tip parametra. URL izgleda ovako:
	 * <code>rest/demo/testheader</code>
	 * 
	 * @param ua
	 *            HeaderParam tip parametra; ovakav parametar je deo HTTP
	 *            request zaglavlja.
	 * @return Vraæa odštampane primljene parametre.
	 */
	@GET
	@Path("/testheader")
	public String getBooks(@HeaderParam("Cookie") String ck) {
		return "/rest/demo/testheader received HeaderParam 'Cookie': " + ck;
	}

	/**
	 * Testira CookieParam tip parametra. URL izgleda ovako:
	 * <code>rest/demo/testcookie</code>
	 * 
	 * @param id
	 *            CookieParam tip parametra; ovakav parametar je deo HTTP
	 *            request zaglavlja.
	 * @return Vraæa odštampane primljene parametre.
	 */
	@GET
	@Path("/testcookie")
	@Produces(MediaType.TEXT_HTML)
	public Response getBooks(@CookieParam("pera") javax.ws.rs.core.Cookie id) {
		if (id == null) {
			NewCookie ck = new NewCookie("pera", "Perin kolacic");
			return Response
					.ok("/rest/demo/testcookie <b>created</b> CookieParam 'pera': " + ck.getValue(), MediaType.TEXT_HTML)
					.cookie(ck).build();
		}
		return Response
				.ok("/rest/demo/testcookie <i><u>received</u></i> CookieParam 'pera': " + id, MediaType.TEXT_HTML)
				.build();
	}
	
	/**
	 * Testira FormParam tip parametra. Ako postoji forma koja je poslata, 
	 * FormParam onda mora da sadži ime parametra forme.
	 * 
	 * @param ime
	 *            vrednost 'ime' parametra forme.
	 * @param prezime
	 *            vrednost 'prezime' parametra forme.
	 * @return Vraæa odštampane primljene parametre.
	 */
	@POST
	@Path("/testform")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String testform(@FormParam("ime") String ime, 
			@FormParam("prezime") String prezime) {
	     return "/rest/demo/testform received @FormParam('ime') " + ime + ", and @FormParam('prezime'): " + prezime;
	}

	 
	// Ovo radi u JBoss-ovoj resteasy implementaciji JAX-RS
//	@POST
//	@Path("/testformS")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public Student testformS(@Form Student s) {
//	     return s;
//	}
	
	
	
	/**
	 * Testira MultivaluedMap tip parametra, koji se koristi kod formi.  
	 * Ova vrsta parametra zapravo mapira parametre forme na asocijativnu mapu.
	 * 
	 * @param s
	 *            Asocijativna mapa svih parametara forme.
	 * @return Vraæa odštampane primljene parametre.
	 */
	@POST
	@Path("/testform2")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String testform2(MultivaluedMap<String, String> s) {
	     return "/rest/demo/testform received MultivaluedMap.get('ime'): " 
	    		 + s.get("ime").toString() 
	    		 + ", MultivaluedMap.get('prezime'): " 
	    		 + s.get("prezime").toString();
	}
	
	/**
	 * Demonstrira slanje i vraæanje objekata enkodiranih u JSON obliku. 
	 * 
	 * @param st
	 *           JSON string koji reprezentuje objekat klase Student.
	 * @return 	 JSON string koji reprezentuje objekat klase Student.
	 */
	@POST
	@Path("/testjson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Student testjon(Student st) {
		System.out.println(st);
		st.setIme(st.getIme() + "2");
		st.setPrezime(st.getPrezime() + "2");
		return st;
	}

	/**
	 * Demonstrira injektovanje HTTP zahteva u parametre metode. 
	 * Injektovani zahtev æemo iskoristiti da iz njega izvuèemo sesiju,
	 * a nju æemo iskoristiti da vežemo objekat klase User na sesiju, pod imenom 'user'.
	 * 
	 * @param request
	 *           Injektovano zaglavlje HTTP zahteva.
	 * @param request
	 *           JSON string koji reprezentuje objekat klase User.
	 * @return 	 JSON string koji reprezentuje objekat klase User.
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request, User user) {
	  User retVal = null;
	  retVal =  (User)request.getSession().
			getAttribute("user");
	  if (retVal == null) {
		  request.getSession().setAttribute("user", user);
		  retVal = user;
	  }
	  return retVal;
	}
	
	/**
	 * Proverava da li u sesiji postoji objekat pod imenom 'user'.
	 * 
	 * @param request
	 *           Injektovano zaglavlje HTTP zahteva.
	 * @param request
	 *           JSON string koji reprezentuje objekat klase User.
	 * @return 	 Informacija o tome da li je objekat klase User zakaèen na sesiju, ili ne.
	 */
	@GET
	@Path("/testlogin")
	@Produces(MediaType.TEXT_PLAIN)
	public String testLogin(@Context HttpServletRequest request) {
	  User retVal = null;
	  retVal =  (User)request.getSession().
			getAttribute("user");
	  if (retVal == null) {
		  return "No user logged in.";
	  }
	  return "User " + retVal + " logged in.";
	}
	
	/**
	 * Invalidira sesiju.
	 * 
	 * @param request
	 *           Injektovano zaglavlje HTTP zahteva.
	 * @return 	 Potvrda invalidacije sesije.
	 */
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean logout(@Context HttpServletRequest request) {
	  User user = null;
	  user =  (User)request.getSession().
			getAttribute("user");
	  if (user != null) {
		  request.getSession().invalidate();
	  }
	  return true;
	}

	
}
