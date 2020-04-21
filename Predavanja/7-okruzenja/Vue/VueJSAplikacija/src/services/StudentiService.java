package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/studenti")
public class StudentiService {

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		System.out.println("test");
		return "OK";
	}

	@GET
	@Path("/getalljson")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getStudents() {
		try {
			// ucitavanje MySQL drajvera
			Class.forName("com.mysql.jdbc.Driver");
			// konekcija
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/njp", "root", "root");
			/*
			// Obtain our environment naming context 
			Context ctx = new InitialContext(); 
			// Look  up our data source 
			DataSource ds = (DataSource)ctx.lookup("java:jboss/datasources/NJPVezbeDS"); 
			Connection conn = ds.getConnection();
			*/
			
			// Slanje upita
			String query = "SELECT * FROM studenti";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			List<Student> studenti = new ArrayList<Student>();
			Student s;
				// Citanje rezultata upita
			while (rset.next()) {
				s = new Student(rset.getString("jmbg"),
						rset.getString("ime"), rset.getString("prezime"),
						rset.getString("broj_indeksa"),
						rset.getDate("datum_rodjenja"));
				studenti.add(s);
			}
			// zatvaranje rezultata i upita
			rset.close();
			stmt.close();
			conn.close();
			return studenti;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@POST
	@Path("/updatejson")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveStudent(Student selectedStudent) {
		System.out.println("saving student: " + selectedStudent);
		try {
			// ucitavanje MySQL drajvera
			Class.forName("com.mysql.jdbc.Driver");
			// konekcija
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/njp", "root", "root");
			/*
			 * // Obtain our environment naming context 
			 * Context envCtx =
			 * (Context) new InitialContext(). lookup("java:comp/env"); 
			 * // Look  up our data source 
			 * DataSource ds = (DataSource)envCtx.lookup("jdbc/njp"); 
			 * Connection conn = ds.getConnection();
			 */

			// Slanje upita
			String query = "UPDATE studenti set ime=?, prezime=?, broj_indeksa=?, datum_rodjenja=? where jmbg=?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, selectedStudent.getIme());
			pstmt.setString(2, selectedStudent.getPrezime());
			pstmt.setString(3, selectedStudent.getBrojIndeksa());
			System.out.println(selectedStudent.getDatumRodjenja());
			pstmt.setDate(4, new java.sql.Date(selectedStudent
					.getDatumRodjenja().getTime()));
			pstmt.setString(5, selectedStudent.getJmbg());
			System.out.println(pstmt.executeUpdate());

			pstmt.close();
			conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
