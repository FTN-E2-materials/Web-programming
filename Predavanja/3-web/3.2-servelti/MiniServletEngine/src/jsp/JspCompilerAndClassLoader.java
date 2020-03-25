package jsp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import servletengine.HttpServlet;

/** Wrapper za java kompajler i class loader.
 * 
 * @author minja
 *
 */
public class JspCompilerAndClassLoader {
	/** Kompajlira servlet nastao od JSP stranice.
	 * Da bi ova metoda ispravno radila, potrebno je da na 
	 * računaru postoji instaliran JDK. 
	 */
	public static boolean compile(File jspFile) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		return compiler.run(null, null, null, jspFile.getPath()) == 0;
	}

	/** Učitava kompajlirani servlet u virtualnu mašinu i pravi
	 * objekat te klase.
	 * @param rootFolder Folder u kojem se nalazi kompajlirani kod.
	 * @param className Ime java klase servleta.
	 * @return Vraća instancu kreiranog servleta, ako je sve OK. Inače vraća null.
	 */
	public static HttpServlet addServlet(File rootFolder, String className) {
		try {
			// Zahvaljujući činjenici da svaki put pravimo nov
			// class loader, moguće je da imamo 
			// u virtalnoj mašini učitano više klasa istog imena i paketa
			// Klase se zapravo razlikuju po class loaderu, paketu i imenu!
			URLClassLoader classLoader = URLClassLoader
					.newInstance(new URL[] { rootFolder.toURI().toURL() });
			Class<?> cls = Class.forName(className, true, classLoader);
			HttpServlet retVal = (HttpServlet) cls.newInstance();
			System.out.println("Dodao " + cls.getName() + " u virtualnu mašinu uz pomoć class loadera: " + classLoader);
			return retVal;
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
