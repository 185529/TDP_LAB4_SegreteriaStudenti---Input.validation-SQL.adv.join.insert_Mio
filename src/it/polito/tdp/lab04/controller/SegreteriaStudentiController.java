package it.polito.tdp.lab04.controller;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	private Model model;
	
	private List<Corso> corsi = new LinkedList<Corso>();
	private List<Studente> iscritti = new LinkedList<Studente>();

	@FXML
	private ComboBox<Corso> comboCorso;

	@FXML
	private Button btnCercaIscrittiCorso;

	@FXML
	private Button btnCercaCorsi;

	@FXML
	private Button btnCercaNome;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	private Button btnIscrivi;

	@FXML
	private TextField txtMatricola;

	@FXML
	private Button btnReset;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCognome;

	public void setModel(Model model) {
		
		this.model = model;
		
		doLoadCorsi();

	}

	@FXML
	void doReset(ActionEvent event) {

		txtMatricola.clear();
		txtNome.clear();
		txtCognome.clear();
		txtResult.clear();
		comboCorso.setValue(new Corso("", 0, "", 0));
		
	}

	@FXML
	void doCercaNome(ActionEvent event) {
		
		int matricola = 0;
		
		try {
			matricola = Integer.parseInt(txtMatricola.getText());
		} catch (NumberFormatException e) {
			
			txtResult.setText("ERROR: Insert a numerical value for field \"matricola\".");
			
			e.printStackTrace();
			
			return;
		}
		
		Studente studente = model.getStudenteByMatricola(matricola);
		
		if(studente == null){
			txtResult.setText("No student found.");
		}else{
			txtNome.setText(studente.getNome());
			txtCognome.setText(studente.getCognome());
		}

	}

	@FXML
	void doCercaIscrittiCorso(ActionEvent event) {
		
		txtResult.clear();
		
		Corso selezione = comboCorso.getValue();
		
		if(selezione.getNome() == ""){
			txtResult.setText("ERROR: Select a subject.");
			return;
		}
		
		iscritti = model.getIscritti(selezione);
		
		if(iscritti.isEmpty()){
			txtResult.setText("No students for subject "+selezione.getNome()+".");
		}else{
			for(Studente s : iscritti){
				txtResult.appendText(s.getMatricola()+"\t"+s.getCognome()+"\t"+s.getNome()+"\t"+s.getCds()+"\n");
			}
		}

	}

	@FXML
	void doCercaCorsi(ActionEvent event) {
		
		txtResult.clear();
		
		int matricola = 0;
		
		try {
			matricola = Integer.parseInt(txtMatricola.getText());
		} catch (NumberFormatException e) {
			
			txtResult.setText("ERROR: Insert a numerical value for field \"matricola\".");
			
			e.printStackTrace();
			
			return;
		}
		
		Studente studente = model.getStudenteByMatricola(matricola);
		
		List<Corso> corsi = new LinkedList<Corso>();
		
		if(studente == null){
			txtResult.setText("ERROR: Student not found in database.");
		}else{
			corsi = model.getCorsi(studente);
			if(corsi.isEmpty()){
				txtResult.setText("No subject found for student ID "+studente.getMatricola()+".");
			}else{
				for(Corso c : corsi){
					txtResult.appendText(c.getCodIns()+"\t"+c.getCrediti()+"\t"+c.getPd()+"\t"+c.getNome()+"\n");
				}
			}
		}

	}

	@FXML
	void doIscrivi(ActionEvent event) {
		
		txtResult.clear();
		
		int matricola = 0;
		
		try {
			matricola = Integer.parseInt(txtMatricola.getText());
		} catch (NumberFormatException e) {
			
			txtResult.setText("ERROR: Insert a numerical value for field \"matricola\".");
			
			e.printStackTrace();
			
			return;
		}
		
		Corso selezione = comboCorso.getValue();
		
		if(selezione.getNome() == ""){
			txtResult.setText("ERROR: Select a subject.");
			return;
		}
		
		if(model.iscrivi(matricola, selezione)){
			txtResult.setText("Student added.");
		}else{
			txtResult.setText("ERROR: Subscription already exists.");
		}

	}
	
	void doLoadCorsi(){
		
		Corso c = new Corso("", 0, "", 0);
		
		comboCorso.getItems().add(c);
		
		corsi = model.loadCorsi();
		
		comboCorso.getItems().addAll(corsi);
		
		comboCorso.setValue(c);
		
	}

	@FXML
	void initialize() {
		assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaNome != null : "fx:id=\"btnCercaNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";		
	}

}
