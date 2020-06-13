package services;

import java.net.URISyntaxException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.PacijentDAO;
import model.Pacijent;

@Path("/pacijenti")
public class PacijentiService {

	
	@Context
	ServletContext ctx;
	
	public PacijentiService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// NAPOMENA!!!
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("pacijentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("pacijentDAO", new PacijentDAO(contextPath));
		}
	}
	
	@GET
	@Path("/ucitavanje")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Pacijent> getSvi() {
		System.out.println("Pozvano ucitavanjeee");
		PacijentDAO pacijentDAO = (PacijentDAO) ctx.getAttribute("pacijentDAO");
		
		for (Pacijent pacijent : pacijentDAO.findAll()) {
			System.out.println("pacijentovo ime: "+ pacijent.getIme() +"\n");
		}
		return pacijentDAO.findAll();
	}
	
	
	@POST
	@Path("/registrujMe")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String registrujMe(){
		System.out.println("\n\ntDOSLA JE REGISTRACIJA\n\n");
		
		// kao neka redirekcija, klijent ce ovo primiti i onda treba da se tu preusme
		return "http://localhost:8080/PocetniREST/pacijentiREST.html";
	}
	
	@PUT
	@Path("/dodaj")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pacijent dodavanjePacijenta( Pacijent pacijentZaDodavanje) {
		
		PacijentDAO pacijentDAO = (PacijentDAO) ctx.getAttribute("pacijentDAO");
		pacijentDAO.addPacijenta(pacijentZaDodavanje);
		
		return pacijentZaDodavanje;
	}
	
	@PUT
	@Path("/izmena")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Pacijent> makeUpdate(Pacijent pacijent) {
		System.out.println("Pozvanaaa izmena podataka");
		
		
		PacijentDAO pacijentDAO = (PacijentDAO) ctx.getAttribute("pacijentDAO");
		pacijentDAO.changePacijenta(pacijent);
		
		return pacijentDAO.findAll();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
