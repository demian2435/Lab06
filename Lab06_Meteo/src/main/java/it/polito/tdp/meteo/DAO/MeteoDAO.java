package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE Localita = ? and Data BETWEEN ? and ? ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			st.setString(2, "2013-" + mese + "-01");
			st.setString(3, "2013-" + mese + "-31");

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<String> getAllLocalita() {

		final String sql = "SELECT Localita FROM situazione GROUP BY localita";

		List<String> localita = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				localita.add(rs.getString("Localita"));
			}

			conn.close();
			return localita;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Citta> getSetUpRicorsiva(int mese) {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE Data BETWEEN ? and ? ORDER BY data ASC";

		List<Citta> setUpRicorsiva = new ArrayList<Citta>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, "2013-" + mese + "-01");
			st.setString(2, "2013-" + mese + "-15");

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String nomeCitta = rs.getString("Localita");

				if (!(setUpRicorsiva.contains(new Citta(nomeCitta)))) {
					setUpRicorsiva.add(new Citta(nomeCitta));
				}
				
				Rilevamento r = new Rilevamento(nomeCitta, rs.getDate("Data"), rs.getInt("Umidita"));
				setUpRicorsiva.get(setUpRicorsiva.indexOf(new Citta(nomeCitta))).addRilevamento(r);
			}

			conn.close();
			return setUpRicorsiva;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
