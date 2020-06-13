package dao;

import java.util.ArrayList;
import bean.Pacijent;
import bean.PatientDTO;


public class PacijentDAO {

	private ArrayList<Pacijent> pacijenti = new ArrayList<>();
	
	
	public PacijentDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public PacijentDAO(String contextPath) {
		loadPacijenti(contextPath);
	}
	
	
	
	
	public ArrayList<Pacijent> findAll() {
		return pacijenti;
	}
	
	
	private void loadPacijenti(String contextPath) {
		pacijenti.add(new Pacijent("DNV/12345", "Marko", "Markovic", "01.10.1990", "musko", "IZLECEN"));
		pacijenti.add(new Pacijent("DEL/54321", "Nikola", "Niklic", "01.10.1999", "musko", "ZARAZEN"));
	}
	
	public void savePacijent(PatientDTO p) {
	
		pacijenti.add(new Pacijent(p.brZdravstvenogOsig, p.ime, p.prezime, p.datumRodjenja, p.pol, p.zdravstveniStatus));
	}
	
	public void changeZdravstveniStatus(String brZdravstvenogOsig) {
		for (Pacijent pacijent : pacijenti) {
			if(pacijent.getBrZdravstvenogOsig().equals(brZdravstvenogOsig)) {
				pacijent.setZdravstveniStatus("ZARAZEN");
			}
		}
	}
}

