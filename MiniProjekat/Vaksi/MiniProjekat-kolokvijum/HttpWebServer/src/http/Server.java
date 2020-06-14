package http;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import dao.StudentDAO;
import model.Student;

public class Server {
	private int port;
	private ServerSocket serverSocket;
	private List<Student> studenti;
	private StudentDAO studentiDAO;
	
	public Server(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		this.studenti = new ArrayList<Student>();
		
		this.studentiDAO = new StudentDAO();
		
		for (Student student : studentiDAO.findAll()) {
			studenti.add(student);
		}
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
				//pogodjeniPath = pogodjeniPath.replace('/', File.separatorChar);
				
				// TODO: Resiti da lepo uzme / kod indeksa
				
				// sigurnosna provera
				if (pogodjeniPath == null)
					continue;

				// podesavam koja datoteka je default
				if (pogodjeniPath.equals(""))
					pogodjeniPath = "static/index.html";
				
				
				
				/*
				 * U formi(html) stavim akciju da gadja recimo 'registruj', 'trazi', 'brisanje'
				 * i onda ovde hendlujem sta ce se desavati u zavisnosti ako
				 * je gadjani path tako pocinjao.
				*/
				if(pogodjeniPath.startsWith("registrujstudenta")) {
					registracijaStudentaResponse(trenutniSoket, pogodjeniPath);
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
	 * Dinamicki zahtev koji se obradjuje kada neko pogodi path za
	 * registraciju, trenutno je taj path /registrujstudenta
	 * @param trenutniSoket
	 * @param nazivTrazeneDatoteke
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void registracijaStudentaResponse(Socket trenutniSoket, String pogodjeniPath)
			throws UnsupportedEncodingException, IOException { 
		
		// /registrujstudenta?brojIndeksa=RA1/2018&ime=Pero...
		String [] citavPath = pogodjeniPath.split("\\?");
		String koristanDeo = citavPath[1];	// brojIndeksa=RA1/2018&ime=Pero..
		
		String [] poljaForme = koristanDeo.split("&"); // svaki element je sad jedno polje forme brojIndeksa=RA1/2018 i sl
		
		String poljeIndeksa = poljaForme[0];
		String [] deloviPolja = poljeIndeksa.split("=");
		String brojIndeksa = deloviPolja[1];
		brojIndeksa = URLDecoder.decode(brojIndeksa, "utf-8");
		
		String poljeImena = poljaForme[1];
		String [] deloviImena = poljeImena.split("=");
		String ime = deloviImena[1];
		
		String poljePrezimena = poljaForme[2];
		String [] deloviPrezimena = poljePrezimena.split("=");
		String prezime = deloviPrezimena[1];
		
		String poljeBodova = poljaForme[3];
		String [] deloviBodova = poljeBodova.split("=");
		String bodoviStr = deloviBodova[1];
		
		int bodovi = Integer.parseInt(bodoviStr);
		
		
		studenti.add(new Student(brojIndeksa, ime, prezime, bodovi));
		System.out.println("\n\n dodao sam studenta: " + ime + " " + prezime + " " + bodovi + " " + brojIndeksa);
		
		PrintWriter stampacOdgovora = new PrintWriter(new OutputStreamWriter(trenutniSoket.getOutputStream(), "utf-8"), true);
		ispisTabeleSaNavigacijom(stampacOdgovora);
		
		stampacOdgovora.close();
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

	

	
	
	
	private void ispisTabeleSaNavigacijom(PrintWriter stampacOdgovora) {
		stampacOdgovora.print("HTTP/1.1 200 OK\r\nContent-type: text/html;charset=utf-8\r\n\r\n");
		String stilTabele = "<style> table, th, td { border: 1px solid black; } </style>";
		stampacOdgovora.println(
				"<html><head>"+stilTabele+"<meta http-equiv=\"Content-type\" value=\"text/html;charset=utf-8\"/></head><body align =\"center\"><h1> HTTP Pregled studenata [PAGINACIJA] </h1><br>");   
		String tabelaStudenata = generisanjeTabeleStudenata();
		stampacOdgovora.println(tabelaStudenata);
	
	}
	
	
	
	private String generisanjeTabeleStudenata() {
		String tabelaKorisnika="<table align=\"center\" >";
		tabelaKorisnika += "<tr> <th> Broj indeksa </th> <th> Ime </th> <th> Prezime </th> <th> Bodovi </th> </tr>";
		
		for (Student student : studenti) {
			tabelaKorisnika += "<tr ";
			
			if(student.getBodovi() < 51) {
				tabelaKorisnika += " style =\"background-color:red;\" ";
			}
			tabelaKorisnika +=">";
			
			tabelaKorisnika += "<td>" + student.getBrojIndeksa() + "</td>";
			tabelaKorisnika += "<td>" + student.getIme() + "</td>";
			tabelaKorisnika += "<td>" + student.getPrezime()+ "</td>";
			tabelaKorisnika += "<td>" + student.getBodovi() + "</td>";
			
			tabelaKorisnika += "</tr>";
		}
		
		tabelaKorisnika += "</table><br>";
		
		return tabelaKorisnika;
	}
	

	
	
	
}
