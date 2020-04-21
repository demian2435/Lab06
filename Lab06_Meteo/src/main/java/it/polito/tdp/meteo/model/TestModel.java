package it.polito.tdp.meteo.model;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();

		// System.out.println(m.getUmiditaMedia(new Mese("Gennaio", 1)));
		Percorso test = m.trovaSequenza(new Mese("Febbraio", 7));

		for (int i = 0; i < test.getRilevamenti().size(); i++) {
			System.out.println(i + 1 + " - " + test.getRilevamenti().get(i));
		}
		
		System.out.println("+++++++++++++");
		
		for (String key : test.getCounter().keySet()) {
			System.out.println(key + " - " + test.getCounter().get(key).intValue());
		}

	}

}
