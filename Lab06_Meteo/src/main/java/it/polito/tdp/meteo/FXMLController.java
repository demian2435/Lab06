/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Mese;
import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxMese"
	private ChoiceBox<Mese> boxMese; // Value injected by FXMLLoader

	@FXML // fx:id="btnUmidita"
	private Button btnUmidita; // Value injected by FXMLLoader

	@FXML // fx:id="btnCalcola"
	private Button btnCalcola; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaSequenza(ActionEvent event) {

	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		txtResult.clear();

		String result = model.getUmiditaMedia(boxMese.getValue());

		txtResult.appendText(result);
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;

		boxMese.getItems().setAll(new Mese("Gennaio", 1), new Mese("Febbraio", 2), new Mese("Marzo", 3),
				new Mese("Aprile", 4), new Mese("Maggio", 5), new Mese("Giugno", 6), new Mese("Luglio", 7),
				new Mese("Agosto", 8), new Mese("Settembre", 9), new Mese("Ottobre", 10), new Mese("Novembre", 11),
				new Mese("Dicembre", 12));
		boxMese.getSelectionModel().selectFirst();
	}
}
