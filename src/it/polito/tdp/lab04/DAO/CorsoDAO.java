package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {

				Corso corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				corsi.add(corso);
				
			}

			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		// TODO
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		final String sql = "SELECT studente.* FROM studente, iscrizione WHERE studente.matricola = iscrizione.matricola AND iscrizione.codins = ?";
		
		List<Studente> iscritti = new LinkedList<Studente>();

		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodIns());

			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {

				Studente studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				iscritti.add(studente);
				
			}
			
			return iscritti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	
	}

	/*
	 * Data una matricola ed il codice insegnamento,
	 * iscrivi lo studente al corso.
	 */
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		
		final String sql1 = "SELECT * FROM iscrizione WHERE matricola=? AND codins=?";
		final String sql2 = "INSERT INTO iscrizione (matricola, codins) VALUES (?, ?)";

		try {
			
			Connection conn = ConnectDB.getConnection();
			
			System.out.println("OK - Connection 1 established.");
			
			PreparedStatement st1 = conn.prepareStatement(sql1);
			
			System.out.println("OK - Statement 1 prepared.");
			
			st1.setInt(1, studente.getMatricola());
			st1.setString(2, corso.getCodIns());
			
			System.out.println("OK - Param 1 binded.");
			
			ResultSet rs = st1.executeQuery();
			
			System.out.println("OK - Query 1 executed.");
						
			if(rs.next()){
				rs.close();
				return false;
			}
			
			conn.close();
			
			conn = ConnectDB.getConnection();
			
			System.out.println("OK - Connection 2 established.");
			
			PreparedStatement st2 = conn.prepareStatement(sql2);
			
			System.out.println("OK - Statement 2 prepared.");

			st2.setInt(1, studente.getMatricola());
			st2.setString(2, corso.getCodIns());
			
			System.out.println("OK - Param 2 binded.");

			st2.executeQuery();
			
			System.out.println("OK - Query 2 executed.");

			st2.close();
			conn.close();
			
			return true;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
}
