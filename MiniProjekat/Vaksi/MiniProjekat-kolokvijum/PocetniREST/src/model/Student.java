package model;

/**
 * Model studenta datog u zadatku
 * @author Vaxi
 *
 */
public class Student {

	
	private String brojIndeksa;
	private String ime;
	private String prezime;
	private Integer bodovi;
	
	public Student() {
		
	}
	
	public String getBrojIndeksa() {
		return brojIndeksa;
	}public Student(String brojIndeksa, String ime, String prezime, Integer bodovi) {
		super();
		this.brojIndeksa = brojIndeksa;
		this.ime = ime;
		this.prezime = prezime;
		this.bodovi = bodovi;
	}

	
	public void setBrojIndeksa(String brojIndeksa) {
		this.brojIndeksa = brojIndeksa;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public Integer getBodovi() {
		return bodovi;
	}
	public void setBodovi(Integer bodovi) {
		this.bodovi = bodovi;
	}
	
	
	
	
	
	
}
