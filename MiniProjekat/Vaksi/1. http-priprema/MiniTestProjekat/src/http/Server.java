package http;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private int port;
	private ServerSocket serverSocket;
	private List<String> korisniciAplikacije;

	public Server(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		this.korisniciAplikacije = new ArrayList<String>();
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
				String nazivTrazeneDatoteke = this.getResource(trenutniSoket.getInputStream());

				// sigurnosna provera
				if (nazivTrazeneDatoteke == null)
					continue;

				// podesavam koja datoteka je default
				if (nazivTrazeneDatoteke.equals(""))
					nazivTrazeneDatoteke = "statickeDatoteke/index.html";
				
				/*
				 * U formi(html) stavim akciju da gadja recimo 'registruj', 'trazi', 'brisanje'
				 * i onda ovde hendlujem sta ce se desavati u zavisnosti ako
				 * je gadjani path tako pocinjao.
				*/
				if (nazivTrazeneDatoteke.startsWith("registruj?imeKorisnika=")) {
					registracijaResponse(trenutniSoket, nazivTrazeneDatoteke);
				} else if (nazivTrazeneDatoteke.startsWith("trazi?imeKorisnika=")) {
					pretragaResponse(trenutniSoket, nazivTrazeneDatoteke);
				} else if (nazivTrazeneDatoteke.startsWith("obrisi?imeKorisnika=")) {
					brisanjeResponse(trenutniSoket, nazivTrazeneDatoteke);

				} else {

					System.out.println("Zahtev stigao od hosta sa imenom: " + adresaIP.getHostName() + ": "
							+ nazivTrazeneDatoteke);

					sendResponse(nazivTrazeneDatoteke, trenutniSoket.getOutputStream());
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

		// ignorisemo ostatak zaglavlja i samo ga bzv ispisemo[ako hocemo]
//		String s1;
//		while (!(s1 = citacStrima.readLine()).equals(""))
//			System.out.println(s1);

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
	 * registraciju, trenutno je taj path /registruj
	 * @param trenutniSoket
	 * @param nazivTrazeneDatoteke
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void registracijaResponse(Socket trenutniSoket, String nazivTrazeneDatoteke)
			throws UnsupportedEncodingException, IOException {
		//TODO: Odraditi ovo na prosiriviji nacin preko splitovanja po ? i =
		int uzmiOdKaraktera = 23;					
		String ime = nazivTrazeneDatoteke.substring(uzmiOdKaraktera);
		ime = URLDecoder.decode(ime, "utf-8");
		System.out.println(ime);
		korisniciAplikacije.add(ime);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(trenutniSoket.getOutputStream(), "utf-8"), true);
		ispisOdgovoraRegistracije(out);

		out.close();
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
		String ime = nazivTrazeneDatoteke.substring(uzmiOdKaraktera);
		ime = URLDecoder.decode(ime, "utf-8");
		System.out.println(ime);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(trenutniSoket.getOutputStream(), "utf-8"), true);
		ispisOdgovoraPretrage(ime, out);

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
	private void ispisOdgovoraRegistracije(PrintWriter stampacOdgovora) {
		stampacOdgovora.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
		String stilTabele = "<style> table, th, td { border: 1px solid black; } </style>";
		
		stampacOdgovora.println(
				"<html><head>"+stilTabele+"<meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body align =\"center\"><h1> Dinamicka stranica</h1><br><h1> Uspesna registracija </h1>");     
		
		String tabelaKorisnika = generisanjeTabeleKorisnika();
		stampacOdgovora.println(tabelaKorisnika);
		
		
		stampacOdgovora.println("<a href=\"http://localhost/statickeDatoteke/index.html\">Pocetna</a><br>");
		
		// Zakomentarisano jer zelim da kada dobije ovu dinamicku stranicu moze samo nazad ka pocetnoj
		// tj da mu malo uvedem restrikciju povodom navigiranja kroz aplikacij(nista spec ali mi lespe ovako)
//		stampacOdgovora.println("<a href=\"http://localhost/statickeDatoteke/pretraga.html\">Pretraga korisnika</a><br>");
//		stampacOdgovora.println("<a href=\"http://localhost/statickeDatoteke/brisanje.html\">Brisanje korisnika</a><br>");
//		stampacOdgovora.println("<a href=\"http://localhost/statickeDatoteke/registracija.html\">Registracija novih korisnika</a><br>");
		stampacOdgovora.println("</body></html>");
	}

	private String generisanjeTabeleKorisnika() {
		String tabelaKorisnika="<table style=\"width:100%\">";
		tabelaKorisnika +="<tr> <th> Ime</th> <th> Prezime </th> <th> Sifra </th> </tr>";
		
		// TODO: Napraviti prave korisnike i onda njihove atribute ispisivati
		for (String imeKorisnika : korisniciAplikacije) {
			tabelaKorisnika += "<tr><td>" + imeKorisnika + "</td>"+ "<td> nema </td>" + "<td> ****</td>" + "</tr>";
		}
		tabelaKorisnika += "</table><br>";
		
		return tabelaKorisnika;
	}

	
	private void ispisOdgovoraPretrage(String ime, PrintWriter stampacOdgovora) {
		stampacOdgovora.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
		String stilTabele = "<style> table, th, td { border: 1px solid black; } </style>";
		
		stampacOdgovora.println(
				"<html><head>"+stilTabele+"<meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body align =\"center\"><h1> Dinamicka stranica</h1><br><h1> Rezultat pretrage </h1>"); 
		
		String tabelaKorisnika = generisanjeTabelePretrageKorisnika(ime);
		stampacOdgovora.println(tabelaKorisnika);
		
		stampacOdgovora.println("<a href=\"http://localhost/statickeDatoteke/index.html\">Pocetna</a><br>");

	}

	private String generisanjeTabelePretrageKorisnika(String ime) {
		String tabelaKorisnika="<table style=\"width:100%\">";
		tabelaKorisnika +="<tr> <th> Ime</th> <th> Prezime </th> <th> Sifra </th> </tr>";
		
		boolean praznaTabela = true;
		for (String imeKorisnika : korisniciAplikacije) {
			if (imeKorisnika.contains(ime)) {
				tabelaKorisnika+="<tr><td>" + imeKorisnika + "</td><td>" + "****" + "</td><td>"+ "zasticena info" + "</td></tr>";
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
		
		if (korisniciAplikacije.contains(ime)) {
			korisniciAplikacije.remove(ime);
			odgovor += "<h1> Korisnik " + ime + " je uspesno izbrisan </h1>";
			
		} else {
			odgovor += "<h1> Korisnik ne postoji u nasem sistemu</h1>";
		}

		stampacOdgovora.println(odgovor);
		stampacOdgovora.println("<a href=\"http://localhost/statickeDatoteke/index.html\">Pocetna</a><br>");
	}

	
	
	
}
