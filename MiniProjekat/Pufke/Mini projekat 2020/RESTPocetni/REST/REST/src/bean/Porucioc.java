package bean;

public class Porucioc {
	public String statusIzrade; // "UIZRADI" "IZRADJENE" "GOTOV
	public String brojIzrade;
	public String imeNarucioca;
	public String brojTelefona;
	public String nazivFoldera;
	public String formatFotografija;
	public String cenaIzrade;
	
	public Porucioc() {
		
	}
	
	public Porucioc(String brojIzrade, String imeNarucioca, String brojTelefona, String nazivFoldera,
			String formatFotografija, String cenaIzrade, String statusIzrade ) {
		super();
		this.brojIzrade = brojIzrade;
		this.imeNarucioca = imeNarucioca;
		this.brojTelefona = brojTelefona;
		this.nazivFoldera = nazivFoldera;
		this.formatFotografija = formatFotografija;
		this.cenaIzrade = cenaIzrade;
		this.statusIzrade = statusIzrade;
	}
	
	
	public String getStatusIzrade() {
		return statusIzrade;
	}

	public void setStatusIzrade(String statusIzrade) {
		this.statusIzrade = statusIzrade;
	}

	public String getBrojIzrade() {
		return brojIzrade;
	}
	public void setBrojIzrade(String brojIzrade) {
		this.brojIzrade = brojIzrade;
	}
	public String getImeNarucioca() {
		return imeNarucioca;
	}
	public void setImeNarucioca(String imeNarucioca) {
		this.imeNarucioca = imeNarucioca;
	}
	public String getBrojTelefona() {
		return brojTelefona;
	}
	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}
	public String getNazivFoldera() {
		return nazivFoldera;
	}
	public void setNazivFoldera(String nazivFoldera) {
		this.nazivFoldera = nazivFoldera;
	}
	public String getFormatFotografija() {
		return formatFotografija;
	}
	public void setFormatFotografija(String formatFotografija) {
		this.formatFotografija = formatFotografija;
	}
	public String getCenaIzrade() {
		return cenaIzrade;
	}
	public void setCenaIzrade(String cenaIzrade) {
		this.cenaIzrade = cenaIzrade;
	}
	
	
	
}