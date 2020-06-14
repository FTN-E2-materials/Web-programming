package services;
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

import dao.StudentDAO;
import model.Student;


@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;
	
	public UserService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// -------------------- NAPOMENA!!! ----------------------------
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("studentiDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("studentiDAO", new StudentDAO());
		}
	}
	
	
	@GET
	@Path("/ucitavanje")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Student> getSvi() {
		
		System.out.println("Pozvano ucitavanjeee");
		
		StudentDAO studentiDAO = (StudentDAO) ctx.getAttribute("studentiDAO");

		return studentiDAO.findAll();
	}
	
	@PUT
	@Path("/dodaj")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Student dodavanjeStudenta( Student studentZaDodavanje) {
		
		StudentDAO studentiDAO = (StudentDAO) ctx.getAttribute("studentiDAO");
		studentiDAO.addStudent(studentZaDodavanje);
		
		return studentZaDodavanje;
	}
	
	
}
