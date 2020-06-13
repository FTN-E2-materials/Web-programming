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

import model.User;

/**
 * Klasa namenjena da ucita korisnike iz fajla i pruža operacije nad njima
 * (poput pretrage). Korisnici se nalaze u fajlu WebContent/users.txt u obliku:
 * firstName;lastName;email;username;password
 * 
 * @author Vaxi
 *
 */
public class UserDAO {

	private Map<String, User> korisnici = new HashMap<>();
	private String putanja;
	
	public UserDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Moze se pristupiti samo
	 *                    iz servleta.
	 */
	public UserDAO(String contextPath) {
		this.putanja = contextPath;
		// Ako bude potrebe za cuvanjem u fajl: ucitavanjeUsera(this.putanja);
		ucitavanjeMokapPodataka();
	}

	
	
	
	
	
	// *************************************** CRUD OPERACIJE ***********************************************
	
	/**
	 * Vraca usera za prosledjeni key. Vraca null ako
	 * korisnik ne postoji
	 * 
	 * @param imePacijenta
	 * @param password
	 * @return
	 */
	public User find(String identifikatorUsera) {
		if (!korisnici.containsKey(identifikatorUsera)) {
			return null;
		}
		User pacijent = korisnici.get(identifikatorUsera);

		return pacijent;
	}

	/**
	 * Dobavljanje vrednosti svih usera
	 * nase aplikacije.
	 * @return Iterabilna kolekcija usera
	 */
	public Collection<User> findAll() {
		return korisnici.values();
	}
	
	/**
	 * Dodavanje prodjenog usera u 
	 * mapu trenutnih usera.
	 * @param pacijent
	 */
	public void addUser(User pacijent) {
		//TODO: U zavisnosti od zadatka, implementirati dodavanje u mapu, voditi racuna sta je KEY a sta VALUE
		
//		if(!korisnici.containsKey(pacijent.getBrojZdravstvenogOsiguranja())) {
//			System.out.println("\n\n\t Dodao sam: " + pacijent.getIme() + " \n\n");
//			korisnici.put(pacijent.getBrojZdravstvenogOsiguranja(), pacijent);
//			
//			saveUsers(); // moram sacuvati izmene
//		}
	}
	
	/**
	 * Izmena usera, setovanje nekih njegovih atributa
	 * u okviru app (ne u fajlu direktno - ako ima tih potreba
	 * potrebna je metoda koja ce uraditi opet cuvanje
	 * svih podataka u fajl)
	 * 
	 * @param prosledjeniPacijent
	 */
	public void changeUser(User prosledjeniPacijent) {
		//TODO: U zavisnosti od zadatka i ako ima potrebe implementirati izmenu korisnika
		
//		Pacijent pacijent = korisnici.get(prosledjeniPacijent.getBrojZdravstvenogOsiguranja());
//		pacijent.setZdravstveniStatus("SA_SIMPTOMIMA");
		
	}
	
	/**
	 * Kreiranje TEST PODATAKA
	 * 
	 * Metoda koja pravi X instanci odredjenog modela
	 * i dodaje ih u nasu mapu usera(koja se kaci
	 * na context aplikacije kasnije, pa je 
	 * stoga dostupna na koriscenje)
	 * 
	 */
	private void ucitavanjeMokapPodataka() {
		
		User u1 = new User("Mico","mico98");
		User u2 = new User("Jovo","jovandeka90");
		User u3 = new User("Dragan","dragance");
		
		korisnici.put(u1.getUsername(), u1);
		korisnici.put(u2.getUsername(), u2);
		korisnici.put(u3.getUsername(), u3);
		
		return;
	}
	
	
	
	
	
	
	
	
	// ********************************** INTERAKCIJA SA FAJLOVIMA ******************************************
	
	/**
	 * Cuvanje usera u fajlu na putanji koja se 
	 * instancirala prilikom instanciranja
	 * pacijentaDAO
	 * 
	 */
	public void saveUsers() {
		BufferedWriter out = null;
		try {
			System.out.println("\n\n\t Cuvam na: " + this.putanja);
			out = Files.newBufferedWriter(Paths.get(this.putanja + "/users.txt"), StandardCharsets.UTF_8);
			for (User u : korisnici.values()) {
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

	/**
	 * Metoda koja vrsi upisivanje u fajl prosledjenog
	 * usera, po izabranom separatoru unutar metode.
	 * @param pacijent
	 * @return
	 */
	private String writeUser(User pacijent) {
		StringBuilder sb = new StringBuilder();
		
		// separator je ';' pa ga zato dodajem
//		sb.append(pacijent.getBrojZdravstvenogOsiguranja()+";");
//		sb.append(pacijent.getIme()+";");
//		sb.append(pacijent.getPrezime()+";");
//		sb.append(pacijent.getDatumRodjenja()+";");
//		sb.append(pacijent.getPol()+";");
//		sb.append(pacijent.getZdravstveniStatus()+";");
		
//		if(pacijent.getRezultatTesta())
//			sb.append("1;");
//		else
//			sb.append("0;");
		
		return sb.toString();
	}
	
	/**
	 * Ucitava userse iz WebContent/users.txt fajla i dodaje ih u mapu
	 * {@link #users}. Kljuc je broj KOJI ZAVISI OD ZADATKA.
	 * 
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void ucitavanjeUsera(String contextPath) {
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
					// TODO: U zavisnosti od modela, ovde ucitati podatke u mapu
//					String brojZdravstvenog = st.nextToken().trim();
//					String imePacijenta = st.nextToken().trim();
//					String prezimePacijenta = st.nextToken().trim();
//					String datumRodjenja = st.nextToken().trim();
//					String polPacijenta = st.nextToken().trim();
//					String zdravstveniStatus = st.nextToken().trim();
//					korisnici.put(brojZdravstvenog, new Pacijent(brojZdravstvenog, imePacijenta, prezimePacijenta, datumRodjenja, polPacijenta, zdravstveniStatus,false));
				
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
