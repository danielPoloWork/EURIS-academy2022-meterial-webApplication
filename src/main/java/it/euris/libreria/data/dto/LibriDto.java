package it.euris.libreria.data.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import it.euris.libreria.data.model.Libri;

public class LibriDto {

	public static ResultSet selectById(Connection conn, Long id) throws SQLException {

		String query = "SELECT * FROM libri WHERE id=?";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setLong(1, id);
		return stmt.executeQuery();
	}

	public static int insert(Connection conn, Libri libri) throws SQLException {

		String query = "INSERT INTO libri (titolo, idautore) VALUES (?,?)";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, libri.getTitolo());
		stmt.setLong(2, libri.getAutore().getId());
		return stmt.executeUpdate();
	}

	public static int update(Connection conn, Libri libriUpdate, Libri libriWhere) throws SQLException {

		String query = "UPDATE libri <SET> WHERE 1=1 ";
		String set = " SET ";
		int cont = 1;
		Map<String, String> hs = new HashMap<>();

		if (libriUpdate.getTitolo() != null) {
			set += "titolo=?";
			hs.put("titoloUpdate", libriUpdate.getTitolo());
		}

		if (libriUpdate.getAutore().getId() > 0) {
			if (!set.trim().equalsIgnoreCase("set")) {
				set += ", ";
			}
			set += "idautore=?";
			hs.put("autoreUpdate", String.valueOf(libriUpdate.getAutore().getId()));
		}

		query = query.replace("<SET>", set);

		if (libriWhere.getTitolo() != null) {
			query += " AND titolo=?";
			hs.put("titoloWhere", libriUpdate.getTitolo());
		}

		if (libriWhere.getAutore().getId() > 0) {
			query += " AND idautore=?";
			hs.put("autoreWhere", String.valueOf(libriUpdate.getAutore().getId()));
		}

		PreparedStatement stmt = conn.prepareStatement(query);
		for (Map.Entry<String, String> entry : hs.entrySet()) {
			stmt.setString(cont, entry.getValue());
			cont = cont + 1;
		}

		return stmt.executeUpdate();
	}

}
