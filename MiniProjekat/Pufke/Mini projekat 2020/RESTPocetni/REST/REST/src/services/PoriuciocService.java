package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import bean.Porucioc;
import bean.PoruciocDTO;
import dao.PoruciocDAO;

@Path("/porucioci")
public class PoriuciocService {

	@Context
	ServletContext ctx;
	
	public PoriuciocService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("poruciocDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("poruciocDAO", new PoruciocDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getPorucioci")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Porucioc> getPorucioci(){
		PoruciocDAO dao = (PoruciocDAO) ctx.getAttribute("poruciocDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/setPorucioci")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void setPorucioci(PoruciocDTO p) {
		PoruciocDAO dao = (PoruciocDAO) ctx.getAttribute("poruciocDAO");
		dao.savePorucioc(p);
	}
	
	@GET
	@Path("/Gotovo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void Gotovo(@QueryParam("brojIzrade") String brojIzrade) {
		PoruciocDAO dao = (PoruciocDAO) ctx.getAttribute("poruciocDAO");
		dao.changeStatusIzrade(brojIzrade);
		System.out.println(brojIzrade);
	}
}
