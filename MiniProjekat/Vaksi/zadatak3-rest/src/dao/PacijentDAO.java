package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import model.Pacijent;

/**
 * Klasa namenjena da ucita korisnike iz fajla i pruža operacije nad njima
 * (poput pretrage). Korisnici se nalaze u fajlu WebContent/users.txt u obliku:
 * firstName;lastName;email;username;password
 * 
 * NAPOMENA: Lozinke se u praksi nikada ne snimaju u cistom tekstualnom obliku.
 * 
 * @author Vaxi
 *
 */
public class PacijentDAO {

	private Map<String, Pacijent> pacijenti = new HashMap<>();
	private String putanja;
	public PacijentDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Moze se pristupiti samo
	 *                    iz servleta.
	 */
	public PacijentDAO(String contextPath) {
		this.putanja = contextPath;
		ucitavanjePacijenata(this.putanja);
	}

	/**
	 * Vraca pacijenta za prosleðeni broj zdravstvenog osiguranja. Vraca null ako
	 * korisnik ne postoji
	 * 
	 * @param imePacijenta
	 * @param password
	 * @return
	 */
	public Pacijent find(String brojZdravstvenog) {
		if (!pacijenti.containsKey(brojZdravstvenog)) {
			return null;
		}
		Pacijent pacijent = pacijenti.get(brojZdravstvenog);

		return pacijent;
	}

	public Collection<Pacijent> findAll() {
		return pacijenti.values();
	}
	
	public void addPacijenta(Pacijent pacijent) {
		if(!pacijenti.containsKey(pacijent.getBrojZdravstvenogOsiguranja())) {
			System.out.println("\n\n\t Dodao sam: " + pacijent.getIme() + " \n\n");
			pacijenti.put(pacijent.getBrojZdravstvenogOsiguranja(), pacijent);
			
			saveUsers(); // moram sacuvati izmene
		}
	}

	public void changePacijenta(Pacijent prosledjeniPacijent) {
		
		Pacijent pacijent = pacijenti.get(prosledjeniPacijent.getBrojZdravstvenogOsiguranja());
		pacijent.setZdravstveniStatus("SA_SIMPTOMIMA");
		
	}
	
	public void saveUsers() {
		BufferedWriter out = null;
		try {
			System.out.println("\n\n\t Cuvam na: " + this.putanja);
			out = Files.newBufferedWriter(Paths.get(this.putanja + "/users.txt"), StandardCharsets.UTF_8);
			for (Pacijent u : pacijenti.values()) {
				out.write(writeUser(u));
				out.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {
				}
			}
		}
		
	}

	private String writeUser(Pacijent pacijent) {
		StringBuilder sb = new StringBuilder();
		
		// separator je ';' pa ga zato dodajem
		sb.append(pacijent.getBrojZdravstvenogOsiguranja()+";");
		sb.append(pacijent.getIme()+";");
		sb.append(pacijent.getPrezime()+";");
		sb.append(pacijent.getDatumRodjenja()+";");
		sb.append(pacijent.getPol()+";");
		sb.append(pacijent.getZdravstveniStatus()+";");
		
//		if(pacijent.getRezultatTesta())
//			sb.append("1;");
//		else
//			sb.append("0;");
		
		return sb.toString();
	}
	
	/**
	 * Ucitava pacijente iz WebContent/users.txt fajla i dodaje ih u mapu
	 * {@link #users}. Kljuc je broj zdravstvenog osiguranja.
	 * 
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void ucitavanjePacijenata(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/users.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String brojZdravstvenog = st.nextToken().trim();
					String imePacijenta = st.nextToken().trim();
					String prezimePacijenta = st.nextToken().trim();
					String datumRodjenja = st.nextToken().trim();
					String polPacijenta = st.nextToken().trim();
					String zdravstveniStatus = st.nextToken().trim();
					pacijenti.put(brojZdravstvenog, new Pacijent(brojZdravstvenog, imePacijenta, prezimePacijenta, datumRodjenja, polPacijenta, zdravstveniStatus,false));
				
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
