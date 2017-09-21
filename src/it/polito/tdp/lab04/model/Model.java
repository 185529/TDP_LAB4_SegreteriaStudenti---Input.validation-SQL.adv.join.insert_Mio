package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private List<Corso> listaCorsi = new LinkedList<Corso>();
	
	public Model(){
		super();
	}

	public List<Corso> loadCorsi() {
		
		CorsoDAO dao = new CorsoDAO();
		
		listaCorsi.addAll(dao.getTuttiICorsi());
			
		return listaCorsi;

	}

	public Studente getStudenteByMatricola(int matricola) {

		StudenteDAO dao = new StudenteDAO();
		
		Studente studente = dao.getByMatricola(matricola);
		
		return studente;
		
	}

	public List<Studente> getIscritti(Corso selezione) {
		
		CorsoDAO dao = new CorsoDAO();
		
		return dao.getStudentiIscrittiAlCorso(selezione);
		
	}

	public List<Corso> getCorsi(Studente studente) {
		
		StudenteDAO dao = new StudenteDAO();
		
		return dao.getCorsiByMatricola(studente.getMatricola());
		
	}

	public boolean iscrivi(int matricola, Corso selezione) {
		
		CorsoDAO dao = new CorsoDAO();
		
		return dao.iscriviStudenteACorso(new Studente(matricola, null, null, null), selezione);
		
	}

}
