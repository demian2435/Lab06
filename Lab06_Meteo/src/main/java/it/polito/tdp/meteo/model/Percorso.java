package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Percorso {
	private List<Rilevamento> rilevamenti;
	private Map<String, Integer> counter;
	private int costo;
	private int counterCambio;

	public Percorso(Rilevamento r) {
		this.rilevamenti = new ArrayList<Rilevamento>();
		this.counter = new HashMap<String, Integer>();
		this.costo = 0;
		this.counterCambio = 0;
		addRilevamento(r);
	}

	public Percorso(Percorso p) {
		this.rilevamenti = new ArrayList<Rilevamento>(p.getRilevamenti());
		this.counter = new HashMap<String, Integer>(p.getCounter());
		this.costo = p.getCosto();
		this.counterCambio = p.getCounterCambio();
	}

	private void counterAdd(String key, String lastCitta) {
		if (counter.containsKey(key)) {
			if (!(getLastCitta().equals(lastCitta))) {
				counter.replace(key, counter.get(key).intValue() + 1);
				this.counterCambio = 1;
			} else {
				counter.replace(key, counter.get(key).intValue() + 1);
				this.counterCambio++;
			}
		} else {
			costo += 100;
			counter.put(key, 1);
			this.counterCambio = 1;
		}
	}

	public void addRilevamento(Rilevamento r) {
		String lastCitta = null;
		try {
			lastCitta = getLastCitta();
		} catch (Exception e) {

		}
		rilevamenti.add(r);
		counterAdd(r.getLocalita(), lastCitta);
		costo += r.getUmidita();
	}

	public List<Rilevamento> getRilevamenti() {
		return rilevamenti;
	}

	public Map<String, Integer> getCounter() {
		return counter;
	}

	public String getLastCitta() {
		return rilevamenti.get(rilevamenti.size() - 1).getLocalita();
	}

	public int getCount(String nome) {
		try {
			return counter.get(nome).intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public int getCosto() {
		return costo;
	}

	public int getCounterCambio() {
		return counterCambio;
	}

}
