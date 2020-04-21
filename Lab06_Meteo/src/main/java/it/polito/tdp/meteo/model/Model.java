package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {

	private MeteoDAO dao;
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private List<Citta> setUpRicorsiva;
	private Percorso result;
	private int bestCosto;

	public Model() {
		dao = new MeteoDAO();
		setUpRicorsiva = new ArrayList<Citta>();
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

	public Percorso trovaSequenza(Mese mese) {
		setUpRicorsiva = dao.getSetUpRicorsiva(mese.getNumero());

		bestCosto = Integer.MAX_VALUE;
		ricorsiva(null, 0);

		return result;
	}

	private void ricorsiva(Percorso parziale, int livello) {

		if (livello == NUMERO_GIORNI_TOTALI) {
			if (parziale.getCounter().size() == setUpRicorsiva.size() && parziale.getCosto() < bestCosto) {
				bestCosto = parziale.getCosto();
				result = new Percorso(parziale);
			}
			return;
		}

		if (livello == 0) {
			for (int citta = 0; citta < setUpRicorsiva.size(); citta++) {
				parziale = new Percorso(setUpRicorsiva.get(citta).getRilevamenti().get(livello));
				ricorsiva(parziale, livello + 1);
			}
			return;
		}

		for (int citta = 0; citta < setUpRicorsiva.size(); citta++) {
			String nomeCitta = setUpRicorsiva.get(citta).getNome();

			if (parziale.getCounterCambio() < NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
				if (parziale.getLastCitta().equals(nomeCitta)) {
					parziale.addRilevamento(setUpRicorsiva.get(citta).getRilevamenti().get(livello));
					ricorsiva(parziale, livello + 1);
					return;
				}
			} else if (parziale.getCount(nomeCitta) < NUMERO_GIORNI_CITTA_MAX - 1) {
				Percorso newP = new Percorso(parziale);
				newP.addRilevamento(setUpRicorsiva.get(citta).getRilevamenti().get(livello));
				ricorsiva(newP, livello + 1);
			}
		}
	}
}
