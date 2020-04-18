package it.polito.tdp.meteo.model;

import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {

	private MeteoDAO dao;
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	public Model() {
		dao = new MeteoDAO();
	}

	public String getUmiditaMedia(Mese mese) {
		List<String> localita = dao.getAllLocalita();

		StringBuffer result = new StringBuffer();
		result.append("Media umidità " + mese.getNome() + " 2013\n");

		for (String l : localita) {
			List<Rilevamento> rc = dao.getAllRilevamentiLocalitaMese(mese.getNumero(), l);
			int somma = 0;
			for (Rilevamento rilevamento : rc) {
				somma += rilevamento.getUmidita();
			}
			double mu = somma / rc.size();
			result.append(l + ": " + mu + "\n");
		}

		return result.toString();
	}

	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		return "TODO!";
	}

}
