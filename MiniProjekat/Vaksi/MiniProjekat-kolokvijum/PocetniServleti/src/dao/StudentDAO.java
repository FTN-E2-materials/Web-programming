package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.Student;

public class StudentDAO {

	private Map<String, Student> studenti = new HashMap<>();
	
	
	public StudentDAO() {
		ucitavanjeMokapPodataka();
	}
	
	public StudentDAO(String putanjaZaSlucajFajlova) {
		ucitavanjeMokapPodataka();
	}
	
	
	/**
	 * Dodavanej studenta u kolekciju studenata
	 * nase aplikacije.
	 * @param student
	 */
	public void addStudent(Student student) {
		if(!studenti.containsKey(student.getBrojIndeksa())) {
			System.out.println("\n\n\t DODAJEM STUDENTA NA BEKU \n\n");
			studenti.put(student.getBrojIndeksa(), student);
		}
		
	}
	
	public void ukloniStudente(ArrayList<Student> studentiZaUklanjanje) {
		for (Student student : studentiZaUklanjanje) {
			if(studenti.containsKey(student.getBrojIndeksa())) {
				studenti.remove(student.getBodovi());
			}
		}
	}
	
	/**
	 * Dobavljanje vrednosti svih studenata
	 * nase aplikacije.
	 * @return Iterabilna kolekcija studenata
	 */
	public Collection<Student> findAll() {
		return studenti.values();
	}
	
	
	/**
	 * Kreiranje TEST PODATAKA
	 * 
	 * Metoda koja pravi X instanci odredjenog modela
	 * i dodaje ih u nasu mapu studenata(koja se kaci
	 * na context aplikacije kasnije, pa je 
	 * stoga dostupna na koriscenje)
	 * 
	 */
	private void ucitavanjeMokapPodataka() {
		
		Student s1 = new Student("RA186/2017", "Vladislav", "Maksimovic",100);
		Student s2 = new Student("RA16/2017", "Miroslav", "Danilovic",90);
		Student s3 = new Student("RA18/2017", "Dragoslav", "Jovic",40);
		Student s4 = new Student("RA86/2017", "Dobrislav", "Radiic",49);
		
		studenti.put(s1.getBrojIndeksa(), s1);
		studenti.put(s2.getBrojIndeksa(), s2);
		studenti.put(s3.getBrojIndeksa(), s3);
		studenti.put(s4.getBrojIndeksa(), s4);
		
	}
}
