package http;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import model.Pacijent;

public class Server {
	private int port;
	private ServerSocket serverSocket;
	private List<Pacijent> pacijenti;
	private Pacijent pacijentZaMenjanje;
	
	public Server(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		this.pacijenti = new ArrayList<Pacijent>();
		this.pacijentZaMenjanje = new Pacijent();
	}

	public void run() {
		System.out.println("Web server trci na portu: " + port);
		System.out.println("Korenski direktorijum je: " + new File("/static").getAbsolutePath() + "\n");

		Socket trenutniSoket;

		while (true) {
			try {
				// prihvataj zahteve
				trenutniSoket = serverSocket.accept();
				InetAddress adresaIP = trenutniSoket.getInetAddress();

				/*
				 * InputStream - mesto gde serveru stize poruka [obicno zahtev od klijenta]
				 * OutputStream - mesto gde upisujemo sve sto zelimo da posaljemo nazad tom
				 * klijentu
				 * 
				 * Obradjujem zahtev od klijenta i dobavljam resurs
				 */
				String pogodjeniPath = this.getResource(trenutniSoket.getInputStream());

				// sigurnosna provera
				if (pogodjeniPath == null)
					continue;

				// podesavam koja datoteka je default
				if (pogodjeniPath.equals(""))
					pogodjeniPath = "WebContent/statickeDatoteke/index.html";
				
				
				
				/*
				 * U formi(html) stavim akciju da gadja recimo 'registruj', 'trazi', 'brisanje'
				 * i onda ovde hendlujem sta ce se desavati u zavisnosti ako
				 * je gadjani path tako pocinjao.
				*/
				if (pogodjeniPath.startsWith("registrujpacijenta")) {
					registracijaResponse(trenutniSoket, pogodjeniPath);
				} else if (pogodjeniPath.startsWith("trazi?imeKorisnika=")) {
					pretragaResponse(trenutniSoket, pogodjeniPath);
				} else if (pogodjeniPath.startsWith("obrisi?imeKorisnika=")) {
					brisanjeResponse(trenutniSoket, pogodjeniPath);

				}else if(pogodjeniPath.startsWith("promenistatus")) {
					promenaStatusaResponse(trenutniSoket, pogodjeniPath);
				}
					
				else {

					System.out.println("Zahtev stigao od hosta sa imenom: " + adresaIP.getHostName() + ": "
							+ pogodjeniPath);

					sendResponse(pogodjeniPath, trenutniSoket.getOutputStream());
				}

				trenutniSoket.close();
				trenutniSoket = null;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	
	/**
	 * Metoda koja prima zahtev od klijenta, obradjuje ga
	 * 
	 * 
	 * @param soketInputStream - mesto gde serveru stize poruka
	 * @return
	 * @throws IOException
	 */
	private String getResource(InputStream soketInputStream) throws IOException {
		BufferedReader citacStrima = new BufferedReader(new InputStreamReader(soketInputStream));
		String ucitanaPoruka = citacStrima.readLine();

		// fail-safe
		if (ucitanaPoruka == null)
			return null;

		/*
		 * Delim na tokene, ili delove ucitane poruke kako bi dobili samo naziv datoteke
		 */
		String[] tokens = ucitanaPoruka.split(" ");

		// prva linija HTTP zahteva: METOD /resurs HTTP/verzija
		// obradjujemo samo GET metodu
		String method = tokens[0];
		if (!method.equals("GET"))
			return null;

		// String resursa(naziv datoteke)
		String nazivDatoteke = tokens[1];

		// izbacimo znak '/' sa pocetka
		nazivDatoteke = nazivDatoteke.substring(1);


		return nazivDatoteke;
	}

	/**
	 * Metoda koja salje odgovor klijentu.
	 * 
	 * 
	 * @param nazivDatoteke       - naziv datoteka koju klijent trazi
	 * @param outputStreamOdgovor - ovde upisujemo resurs koji cemo vratiti nazad
	 *                            klijentu [izlazni stream]
	 * @throws IOException
	 */
	private void sendResponse(String nazivDatoteke, OutputStream outputStreamOdgovor) throws IOException {
		/*
		 * Stampac koji lagano stampa stringove u output stream. Parametar je output
		 * stream u koji stampamo odgovor!
		 */
		PrintStream stampacStringova = new PrintStream(outputStreamOdgovor);

		// zamenimo web separator sistemskim separatorom
		nazivDatoteke = nazivDatoteke.replace('/', File.separatorChar);

		/*
		 * Datoteka koju je klijent zahtevao, ako ona postoji na serveru, uredno ce se
		 * kreirati i mozemo manervisati s njom sada.
		 */
		File zahtevanaDatoteka = new File(nazivDatoteke);

		if (!zahtevanaDatoteka.exists()) {
			// ako datoteka ne postoji, vratimo kod za gresku
			String porukaGreske = "HTTP/1.0 404 Na zalost nemamo tu stranicu\r\n"
					+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Greska: Ne postoji "
					+ zahtevanaDatoteka.getName() + "</b>";

			stampacStringova.print(porukaGreske);

//            ps.flush();
			System.out.println("Nisam uspeo naci resurs: " + zahtevanaDatoteka);
			return;
		}

		// ispisemo zaglavlje HTTP odgovora
		stampacStringova.print("HTTP/1.0 200 OK\r\n\r\n");

		// a, zatim datoteku
		FileInputStream citacBajtovaDatoteke = new FileInputStream(zahtevanaDatoteka);
		byte[] trenutnoIscitaniPodaci = new byte[8192]; // pomocna promenljiva[kao bafer neki]
		int len;

		/*
		 * Citac bajtova iscitava i ubacuje u pomocnu promenljivu trenutno iscitanih
		 * podataka a potom stampac stringova te podatke upisuje u svoj output stream
		 * odnosno u odgovor klijentu, i tako klijent zapravo dobije bajtove odnosno
		 * podatke iz datoteke koja se nalazi na nasem serveru(racunaru)
		 * 
		 * Ako je len == -1 znaci da nemamo vise sta iscitati iz datoteke.
		 */
		while ((len = citacBajtovaDatoteke.read(trenutnoIscitaniPodaci)) != -1)
			stampacStringova.write(trenutnoIscitaniPodaci, 0, len);

		/*
		 * Cistim stampac od prethodno koristenih podataka, kao u zivotu kad koristis i
		 * brljas po stampacu, pa uzmes da ga ocistis kako bi u buduce bio delotvorniji
		 * i da ne bi na sledecem papiru istampao nesto sto je ostalo od prethodnog
		 * koriscenja/stampanja.
		 */
		stampacStringova.flush();
		citacBajtovaDatoteke.close();
	}
	
	/**
	 * Dinamicki zahtev koji se obradjuje kada neko pogodi path za
	 * registraciju, trenutno je taj path /registrujpacijenta
	 * @param trenutniSoket
	 * @param nazivTrazeneDatoteke
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void registracijaResponse(Socket trenutniSoket, String pogodjeniPath)
			throws UnsupportedEncodingException, IOException { 
		
		// /registruj?imePacijenta=blabla , uzimam ovo posle upitnika na obradu
		String [] citavPath = pogodjeniPath.split("\\?");
		String koristanDeo = citavPath[1];		//uzimam samo imePacijenta=Nikola&prezimePacijena=Jovic...
		
		String [] poljaForme = koristanDeo.split("&");	// svaki element je sad jedno polje forme imePrezime=Pero
		
		String poljeBrojZdravstvenogOsiguranja = poljaForme[0];
		String[] brojZdravstvenogOsiguranjaDelovi = poljeBrojZdravstvenogOsiguranja.split("=");
		String brojZdravstvenogOsiguranjaPacijenta = brojZdravstvenogOsiguranjaDelovi[1]; 
		
		String poljeIme = poljaForme[1];
		String[] imePacijentaDelovi = poljeIme.split("=");
		String imePacijenta = imePacijentaDelovi[1]; 
		
		String poljePrezime = poljaForme[2];
		String[] prezimePacijentaDelovi = poljePrezime.split("=");
		String prezimePacijenta = prezimePacijentaDelovi[1]; 
		
		String poljeDatumRodjenja = poljaForme[3];
		String[] datumRodjenjaPacijentaDelovi = poljeDatumRodjenja.split("=");
		String datumRodjenjaPacijenta = datumRodjenjaPacijentaDelovi[1]; 
		
		String poljePol = poljaForme[4];
		String[] polPacijentaDelovi = poljePol.split("=");
		String polPacijenta = polPacijentaDelovi[1]; 
		
		String zdravstveniStatus = poljaForme[5];
		String[] zdravstveniStatusPacijentaDelovi = zdravstveniStatus.split("=");
		String zdravstveniStatusPacijenta = zdravstveniStatusPacijentaDelovi[1];
		
		Pacijent pacijent = new Pacijent(brojZdravstvenogOsiguranjaPacijenta, imePacijenta, prezimePacijenta, datumRodjenjaPacijenta, polPacijenta, zdravstveniStatusPacijenta, false);
		PrintWriter stampacOdgovora = new PrintWriter(new OutputStreamWriter(trenutniSoket.getOutputStream(), "utf-8"), true);
		
		for (Pacijent tempPacijent : pacijenti) {
			if(tempPacijent.getBrojZdravstvenogOsiguranja() == pacijent.getBrojZdravstvenogOsiguranja()) {
				String porukaGreske = "HTTP/1.0 404 Pacijent je vec testiran \r\n"
						+ "Content-type: text/html; charset=UTF-8\r\n\r\n<b>404 Greska: Taj pacijent je vec testiran";
				stampacOdgovora.println(porukaGreske);
				stampacOdgovora.close();
				return;
			}
		}
		pacijenti.add(pacijent);
		ispisTabeleSaPretragom(stampacOdgovora);

		stampacOdgovora.close();
	}
	

	
	/**
	 * Dinamicki zahtev koji se obradjuje kada neko pogodi path za 
	 * pretrazivanje korisnika, trenutno je taj path /trazi
	 * @param trenutniSoket
	 * @param nazivTrazeneDatoteke
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void pretragaResponse(Socket trenutniSoket, String nazivTrazeneDatoteke)
			throws UnsupportedEncodingException, IOException {
		int uzmiOdKaraktera = 19;
		String prezime = nazivTrazeneDatoteke.substring(uzmiOdKaraktera);
		prezime = URLDecoder.decode(prezime, "utf-8");
		System.out.println(prezime);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(trenutniSoket.getOutputStream(), "utf-8"), true);
		ispisOdgovoraPretrage(prezime, out);

		out.close();
	}
	
	
	
	/**
	 * Dinamicki zahtev koji se obradjuje kada neko pogodi path za
	 * brisanje korisnika, taj path je trenutno /obrisi
	 * @param trenutniSoket
	 * @param nazivTrazeneDatoteke
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void brisanjeResponse(Socket trenutniSoket, String nazivTrazeneDatoteke)
			throws UnsupportedEncodingException, IOException {
		int uzmiOdKaraktera = 20;
		String ime = nazivTrazeneDatoteke.substring(uzmiOdKaraktera);
		ime = URLDecoder.decode(ime, "utf-8");
		System.out.println(ime);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(trenutniSoket.getOutputStream(), "utf-8"), true);
		ispisOdgovoraBrisanja(ime, out);

		out.close();
	}
	
	
	
	/**
	 * Ispis dinamickog sadrzaja kada se pogodi path /registruj
	 * @param stampacOdgovora - stampac koji stampa odgovor/stranicu u OutputStream servera
	 */
	private void ispisTabeleSaPretragom(PrintWriter stampacOdgovora) {
		stampacOdgovora.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
		String stilTabele = "<style> table, th, td { border: 1px solid black; } </style>";
		stampacOdgovora.println(
				"<html><head>"+stilTabele+"<meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body align =\"center\"><h1> Dinamicka stranica</h1><br><h1> Uspesna registracija </h1>");     
		
		String tabelaKorisnika = generisanjeTabeleKorisnika();
		stampacOdgovora.println(tabelaKorisnika);
		
		String pretragaKorisnika = generisanjePretrageKorisnika();
		stampacOdgovora.println(pretragaKorisnika);
		
		stampacOdgovora.println("<a href=\"http://localhost/WebContent/statickeDatoteke/index.html\">Pocetna</a><br>");
		stampacOdgovora.println("</body></html>");
	}

	private String generisanjePretrageKorisnika() {
		String pretragaKorisnika="<h1> Pretraga pacijenta po prezimenu: </h1><br>";
		pretragaKorisnika += "<form action =\"http://localhost/trazi\" method=\"GET\" >";
		pretragaKorisnika += "<label> Prezime pacijenta: </label>";
		pretragaKorisnika += "<input type=\"text\" name=\"imeKorisnika\" /><br><br>";
		pretragaKorisnika += "<input type=\"submit\" value=\"Pretrazi\" /><br><br>";
		
		
		
		return pretragaKorisnika;
	}
	
	private String generisanjeTabeleKorisnika() {
		String tabelaKorisnika="<table style=\"width:100%\">";
		tabelaKorisnika +="<tr> <th> Broj zdravstvenog osiguranja </th> <th> Ime pacijenta </th> <th> Prezime pacijenta </th> <th> Datum rodjenja </th> <th> Pol </th> <th> Zdravstveni status </th> <th> Status testa</th> </tr>";
		
		for (Pacijent pacijent : pacijenti) {
			String testJePozitivan = "<a href=\"http://localhost:80/promenistatus";
			testJePozitivan += pacijent.getBrojZdravstvenogOsiguranja();
			testJePozitivan += "\">Test je pozitivan </a>";
			
			tabelaKorisnika += "<tr";
			if(pacijent.getRezultatTesta()) {
				tabelaKorisnika += " style=\"background:red;\"";
				System.out.println("DODAO SAM STIL");
				
			}
			tabelaKorisnika+=">";	// zatvaram pocetak reda, ako je pozitivan na koronu dobice stil crvenog ako ne, nikome nista
			tabelaKorisnika += "<td>" + pacijent.getBrojZdravstvenogOsiguranja() + "</td>";
			tabelaKorisnika += "<td>"+ pacijent.getIme() +"</td>";
			tabelaKorisnika += "<td>" + pacijent.getPrezime() + "</td>";
			tabelaKorisnika += "<td>" + pacijent.getDatumRodjenja() + "</td>";
			tabelaKorisnika += "<td>" + pacijent.getPol() + "</td>";
			tabelaKorisnika += "<td>" + pacijent.getZdravstveniStatus() + "</td>";
			tabelaKorisnika += "<td>";
			if(pacijent.getRezultatTesta()) {
				tabelaKorisnika += "";
			}else {
				tabelaKorisnika += testJePozitivan;
			}
			tabelaKorisnika += "</td>";
			tabelaKorisnika += "</tr>";
		}
		tabelaKorisnika += "</table><br>";
		
		return tabelaKorisnika;
	}

	
	/**
	 * Kada neko pogodi path /promenistatus menjamo status tog pacijenta
	 * na pozitivni rezultat testa na Covid19
	 * @param trenutniSoket
	 * @param pogodjeniPath
	 * @throws IOException
	 */
	private void promenaStatusaResponse(Socket trenutniSoket,String pogodjeniPath) throws IOException {
		int uzmiOdKaraktera = 13;//promenistatus
		String brojZdravstvenogOsiguranja = pogodjeniPath.substring(uzmiOdKaraktera);
		brojZdravstvenogOsiguranja = URLDecoder.decode(brojZdravstvenogOsiguranja, "utf-8");
		
		
		for (Pacijent pacijent : pacijenti) {
			System.out.println(pacijent.getBrojZdravstvenogOsiguranja() + "\n");
			if(pacijent.getBrojZdravstvenogOsiguranja().equals(brojZdravstvenogOsiguranja)) {
				pacijent.setRezultatTesta(true);
				pacijent.setZdravstveniStatus("ZARAZEN");
				System.out.println("MENJAM STATUS NA TRUEEEE");
			}
		}
		
		PrintWriter out = new PrintWriter(new OutputStreamWriter(trenutniSoket.getOutputStream(), "utf-8"), true);
		ispisTabeleSaPretragom(out);

		out.close();

		System.out.println("\n\nAJ DA VIDIMO STA CEMO SAD");
	}
	
	private void ispisOdgovoraPretrage(String prezime, PrintWriter stampacOdgovora) {
		stampacOdgovora.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
		String stilTabele = "<style> table, th, td { border: 1px solid black; } </style>";
		
		stampacOdgovora.println(
				"<html><head>"+stilTabele+"<meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body align =\"center\"><h1> Dinamicka stranica</h1><br><h1> Rezultat pretrage </h1>"); 
		
		String tabelaKorisnika = generisanjeTabeleRezultataPretrage(prezime);
		stampacOdgovora.println(tabelaKorisnika);
		
		stampacOdgovora.println("<a href=\"http://localhost/WebContent/statickeDatoteke/index.html\">Pocetna</a><br>");

	}

	
	
	private String generisanjeTabeleRezultataPretrage(String prezime) {
		String tabelaKorisnika="<table style=\"width:100%\">";
		tabelaKorisnika +="<tr> <th> Broj zdravstvenog osiguranja </th> <th> Ime pacijenta </th> <th> Prezime pacijenta </th> <th> Datum rodjenja </th> <th> Pol </th> <th> Zdravstveni status </th> <th> Status testa</th> </tr>";
		
		boolean praznaTabela = true;
		for (Pacijent pacijent : pacijenti) {
			if (pacijent.getPrezime().contains(prezime)) {
				String testJePozitivan = "<a href=\"http://localhost:80/promenistatus";
				testJePozitivan += pacijent.getBrojZdravstvenogOsiguranja();
				testJePozitivan += "\">Test je pozitivan </a>";
				
				tabelaKorisnika += "<tr";
				if(pacijent.getRezultatTesta()) {
					tabelaKorisnika += " style=\"background:red;\"";
					System.out.println("DODAO SAM STIL");
					
				}
				tabelaKorisnika+=">";	// zatvaram pocetak reda, ako je pozitivan na koronu dobice stil crvenog ako ne, nikome nista
				tabelaKorisnika += "<td>" + pacijent.getBrojZdravstvenogOsiguranja() + "</td>";
				tabelaKorisnika += "<td>"+ pacijent.getIme() +"</td>";
				tabelaKorisnika += "<td>" + pacijent.getPrezime() + "</td>";
				tabelaKorisnika += "<td>" + pacijent.getDatumRodjenja() + "</td>";
				tabelaKorisnika += "<td>" + pacijent.getPol() + "</td>";
				tabelaKorisnika += "<td>" + pacijent.getZdravstveniStatus() + "</td>";
				tabelaKorisnika += "<td>";
				if(pacijent.getRezultatTesta()) {
					tabelaKorisnika += "";
				}else {
					tabelaKorisnika += testJePozitivan;
				}
				tabelaKorisnika += "</td>";
				tabelaKorisnika += "</tr>";
				praznaTabela = false;
			}
		}
		
		tabelaKorisnika += "</table><br>";
		
		if(praznaTabela)
			return "<h2> Trazeni korisnik ne postoji u nasem sistemu</h2>";
		
		return tabelaKorisnika;
	}

	
	private void ispisOdgovoraBrisanja(String ime, PrintWriter stampacOdgovora) {
		stampacOdgovora.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
		
		String odgovor = "<html><head><meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body align=\"center\"><h1> Dinamicka stranica </h1>"; 
		
		if (pacijenti.contains(ime)) {
			pacijenti.remove(ime);
			odgovor += "<h1> Korisnik " + ime + " je uspesno izbrisan </h1>";
			
		} else {
			odgovor += "<h1> Korisnik ne postoji u nasem sistemu</h1>";
		}

		stampacOdgovora.println(odgovor);
		stampacOdgovora.println("<a href=\"http://localhost/WebContent/statickeDatoteke/index.html\">Pocetna</a><br>");
	}

	
	
	
}
