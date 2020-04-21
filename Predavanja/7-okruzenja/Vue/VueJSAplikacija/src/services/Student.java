package services;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
	private static final long serialVersionUID = 5392522395310642063L;
	private String jmbg;
	private String ime;
	private String prezime;
	private String brojIndeksa;
	private Date datumRodjenja;

	public Student() {
		jmbg="";
		ime="";
		prezime="";
		brojIndeksa="";
		datumRodjenja=new Date();
	}
	
	public Student(String jmbg, String ime, String prezime, String brojIndeksa,
			Date datumRodjenja) {
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.brojIndeksa = brojIndeksa;
		this.datumRodjenja = datumRodjenja;
	}
	
	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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
	public String getBrojIndeksa() {
		return brojIndeksa;
	}
	public void setBrojIndeksa(String brojIndeksa) {
		this.brojIndeksa = brojIndeksa;
	}
	public Date getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	@Override
	public String toString() {
		return "[" + ime + " " + prezime + "]";
	}

}
