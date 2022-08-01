package it.euris.libreria.data.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.euris.libreria.data.model.Autori;

public class AutoriDto {
	
	public static int insert(Connection conn, Autori autori) throws SQLException {

		String query = "INSERT INTO autori (nome, cognome) VALUES (?,?)";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, autori.getNome());
		stmt.setString(2, autori.getCognome());
		return stmt.executeUpdate();
	}
	
	public static Autori selectByNomeCognome(Connection conn, String nome, String cognome) throws SQLException {

		String query = "SELECT * FROM autori WHERE nome=? AND cognome=?";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, nome);
		stmt.setString(2, cognome);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return Autori.builder().id(rs.getLong("id")).nome(rs.getString("nome")).cognome(rs.getString("cognome")).build();
		}
		return null;
	}

}
