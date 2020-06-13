package dao;

import java.util.ArrayList;


import beans.Pacijent;

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
}
