package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import bean.Pacijent;
import bean.PatientDTO;
import dao.PacijentDAO;

@Path("/pacijenti")
public class PatientService {

	@Context
	ServletContext ctx;
	
	public PatientService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("pacijentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("pacijentDAO", new PacijentDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getPacijenti")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Pacijent> getPacijenti(){
		PacijentDAO dao = (PacijentDAO) ctx.getAttribute("pacijentDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/setPacijenti")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void setPacijenti(PatientDTO p) {
		PacijentDAO dao = (PacijentDAO) ctx.getAttribute("pacijentDAO");
		dao.savePacijent(p);
	}
	
	@GET
	@Path("/TestPozitivan")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void TestPozitivan(@QueryParam("brZdravstvenogOsig") String brZdravstvenogOsig) {
		PacijentDAO dao = (PacijentDAO) ctx.getAttribute("pacijentDAO");
		dao.changeZdravstveniStatus(brZdravstvenogOsig);
	}
}
