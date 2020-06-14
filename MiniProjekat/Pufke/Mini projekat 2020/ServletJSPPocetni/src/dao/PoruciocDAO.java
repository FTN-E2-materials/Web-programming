package dao;

import java.util.ArrayList;


import beans.Porucioc;

public class PoruciocDAO {

	private ArrayList<Porucioc> porucioci = new ArrayList<>();
	private ArrayList<Porucioc> poruciociPretraga = new ArrayList<>();
	
	public PoruciocDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public PoruciocDAO(String contextPath) {
		loadPorucioci(contextPath);
	}
	
	
	public ArrayList<Porucioc> findAll() {
		return porucioci;
	}
	
	public void pretragaPorucioca(String nazivFoldera){
		poruciociPretraga.clear();
		for(Porucioc porucioc: porucioci) {
			if(porucioc.getNazivFoldera().contains(nazivFoldera)) {
				poruciociPretraga.add(porucioc);
			}
		}
	}
	
	
	
	public ArrayList<Porucioc> getPoruciociPretraga() {
		return poruciociPretraga;
	}



	private void loadPorucioci(String contextPath) {
		porucioci.add(new Porucioc("1106/10", "Pera Peric", "060/0123-123", "perine fotke", "10x15", "176" , "UIZRADI"));
		porucioci.add(new Porucioc("1107/10", "Vasa komsija", "060/1234-123", "New Folder(12)", "21x18", "2340" ,"IZRADJENE"));
	}
}
