package jsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import servletengine.HttpServlet;

/** Kreira servlet na osnovu JSP koda. 
 * Podržava deklaracije, izraze i skriplete.
 * 
 * @author minja
 *
 */
public class ServletBuilder {
	/** Ime JSP datoteke od koje se pravi servlet. */
	String jspFile;
	/** Ime servleta koji nastaje od JSP datoteke. */
	String servletName;
	/** Folder u kojem se nalazi JSP datoteka. */
	File servletFolder;
	/** Objekat klase File koji ukazuje na napravljeni servlet. */
	File servletFile;
	/** Ako prilikom parsiranja naiđemo na otvoreni skriptlet tag,
	 * ova promenljiva prelazi na true. Kada naiđemo na zatvarajuči tag, 
	 * prelazi na false.
	 */
	boolean scriptletStarted = false;
	/** Ako prilikom parsiranja naiđemo na otvoreni tag za deklaraciju,
	 * ova promenljiva prelazi na true. Kada naiđemo na zatvarajuči tag, 
	 * prelazi na false.
	 */
	boolean declarationStarted = false;
	/** Sve deklaracije se prvo smeštaju u ovu listu,
	 * pa se pre zatvaranja datoteke one dštampaju u nju 
	 * (ispod metode service).
	 */
	List<String> deklaracija = new ArrayList<String>();

	public ServletBuilder() {

	}

	public ServletBuilder(String jspFile) {
		this.jspFile = jspFile;
		this.servletName = createServletName(jspFile);
		this.servletFolder = new File(jspFile).getParentFile();
		this.servletFile = new File(servletFolder, this.servletName + ".java");
	}

	/** Kreira ime servleta tako što odbaci imena foldera,
	 * a zameni tačku donjom crtom.
	 * @param f Ime JSP datoteke od kojepravimo ime za servlet
	 * @return ime servleta
	 */
	private static String createServletName(String f) {
		return new File(f).getName().replaceAll("\\.", "_");
	}

	/** Pravi puno ime servleta tako što upotrebi {@link #createServletName} 
	 * ali i uključi imena foldera, odvojene tačkom a ne kosom crtom. 
	 * @SEE createServletName
	 * @param f
	 * @return
	 */
	public static String createFullServletName(String f) {
		return f.replaceAll("\\.", "_").replaceAll("/", ".");
	}

	/** Pravi servlet od JSP stranice.
	 * Zamenjuje svaki JSP izraz redom u kojem se štampa vrednost tog izraza.
	 * Zamenjuje skriptlet konkretnim kodom.
	 * Zamenjuje deklaraciju konkretnim kodom, ali izvan service metode.  
	 * @return Ako uspe da generiše servlet i da ga kompajlira, onda kreira objekat te klase i vraća ga. Inače vraća null.
	 */
	public HttpServlet buildServlet() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(jspFile));
			PrintWriter out = new PrintWriter(new FileWriter(servletFile));
			String s;
			saveHeader(out);
			while ((s = in.readLine()) != null) {
				out.println(transform(s));
			}
			endMethod(out);
			
			saveDeclarations(out);

			saveFooter(out);
			
			out.close();
			in.close();
			if (JspCompilerAndClassLoader.compile(servletFile))
				return JspCompilerAndClassLoader.addServlet(servletFolder, servletName);
			else
				return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** Snima sve deklaracije ispod service metode. */
	private void saveDeclarations(PrintWriter out) {
		for (String s : deklaracija) {
			out.println(s);
		}
	}

	/** Transformiše JSP kod u servletski kod. */
	private String transform(String s) {
		String retVal = "";
		int idxD1 = s.indexOf("<%!");
		int idxE1 = s.indexOf("<%=");
		int idxS1 = s.indexOf("<%");
		int idxS2 = s.indexOf("%>");
		
		if(idxD1!= -1) {
			// imamo deklaraciju
			int idxD2 = s.indexOf("%>");
			if (idxD2 != -1) { 
				// imamo u istom redu i zatvarajući tag za deklaraciju
				String before = s.substring(0, idxD1);
				String content = s.substring(idxD1 + 3, idxD2);
				String after = s.substring(idxD2 + 2);
				retVal = "out.println(\"" + before.replaceAll("\"", "\\\\\"") + "\");";
				deklaracija.add(content);
				retVal += "out.println(\"" + after.replaceAll("\"", "\\\\\"") + "\");";
			} else {
				// nemamo zatvarajući tag za skriptlet u istom redu
				declarationStarted = true;
				deklaracija.add(s.substring(idxS1 + 3));
			}
		} else if (idxE1 != -1) {
			// imamo izraz
			int idxE2 = s.indexOf("%>");
			if (idxE2 != -1) {
				// i zatvarajući tag od izraza je u istom redu
				String before = s.substring(0, idxE1);
				String content = s.substring(idxE1 + 3, idxE2);
				String after = s.substring(idxE2 + 2);
				retVal = "out.println(\"" + before.replaceAll("\"", "\\\\\"") + "\");";
				retVal += "out.println(" + content + ");";
				retVal += "out.println(\"" + after.replaceAll("\"", "\\\\\"") + "\");";
			} else {
				retVal = "out.println(\"" + s.replaceAll("\"", "\\\\\"") + "\");";
			}
		} else if(idxS1!= -1) {
			// imamo skriptlet
			idxS2 = s.indexOf("%>");
			if (idxS2 != -1) { 
				// imamo u istom redu i zatvarajući tag za skriptlet
				String before = s.substring(0, idxS1);
				String content = s.substring(idxS1 + 3, idxS2);
				String after = s.substring(idxS2 + 2);
				retVal = "out.println(\"" + before.replaceAll("\"", "\\\\\"") + "\");";
				retVal += content;
				retVal += "out.println(\"" + after.replaceAll("\"", "\\\\\"") + "\");";
			} else {
				// nemamo zatvarajući tag za skriptlet u istom redu
				scriptletStarted = true;
				retVal = s.substring(idxS1 + 2);
			}
		} else if (idxS2 != -1) {
			// imamo zatvarajući tag za skriptlet ili deklaraciju
			String before = s.substring(0, idxS2);
			String other = s.substring(idxS2 + 2);
			if (scriptletStarted) {
				retVal = before + "\n";
				retVal += "out.println(" + other.replaceAll("\"", "\\\\\"") + ");";
				scriptletStarted = false;
			} else if (declarationStarted) {
				deklaracija.add(before + "\n");
				retVal += "out.println(" + other.replaceAll("\"", "\\\\\"") + ");";
				declarationStarted = false;
			}
		} else {
			if (declarationStarted) {
				deklaracija.add(s);
			} else if (scriptletStarted) {
				retVal = s;
			} else 
				retVal = "out.println(\"" + s.replaceAll("\"", "\\\\\"") + "\");";
		}
		return retVal;
	}

	/** Snima zaglavlje servleta i početak service metode. */
	private void saveHeader(PrintWriter out) {
		out.println("import java.io.IOException;");
		out.println("import java.io.PrintWriter;");
		out.println("");
		out.println("import servletengine.HttpServlet;");
		out.println("import servletengine.HttpServletRequest;");
		out.println("import servletengine.HttpServletResponse;");
		out.println("");
		out.println("public class " + servletName + " extends HttpServlet {");
		out.println("	public void service(HttpServletRequest request, HttpServletResponse response)");
		out.println("		throws IOException {");
		out.println("		System.out.println(\"JSP SERVLET\");");
		out.println("		response.setContentType(\"text/html\");");
		out.println("		PrintWriter out = response.getWriter();");
	}

	/** Snima zatvarajuću vitičastu zagradu u service metodi. */
	private void endMethod(PrintWriter out) {
		out.println("	}");
	}
	
	/** Zatvara java klasu. */
	private void saveFooter(PrintWriter out) {
		out.println("}");
	}

	/** Proverava da li je JSP stranica starijeg datuma od servleta
	 * koji je nastao od nje.
	 * @param resource Ime JSP fajla
	 * @return Vraća true ako je JSP stranica starija od servleta.
	 */
	public static boolean correctDate(String resource) {
		String _servletName = createServletName(resource);
		File _servletFolder = new File(resource).getParentFile();
		File _servletFile = new File(_servletFolder, _servletName + ".java");
		
		File jspFile = new File(resource);
		
		if (jspFile.lastModified() <= _servletFile.lastModified())
			return true;
		else
			return false;
	}
}
