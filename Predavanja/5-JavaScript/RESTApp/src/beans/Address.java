package beans;

import javax.ws.rs.FormParam;

public class Address {
	@FormParam("ulica")
	private String ulica;
	@FormParam("broj")
	private String broj;
	
	public Address() {
		
	}

	public Address(String ulica, String broj) {
		this();
		this.ulica = ulica;
		this.broj = broj;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	@Override
	public String toString() {
		return "Address [ulica=" + ulica + ", broj=" + broj + "]";
	}
}
