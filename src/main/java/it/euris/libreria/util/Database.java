package it.euris.libreria.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Database {

	private Connection conn;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public boolean connetti()
			throws FileNotFoundException, IOException, JDOMException, SQLException, ClassNotFoundException {

		String connectionUrl = null;
		String driver = null;
		String dbUsername = null;
		String dbPassword = null;

		SAXBuilder builder = new SAXBuilder();

		FileReader xmlFile;

		xmlFile = new FileReader("config.xml");

		Document document = (Document) builder.build(xmlFile);
		Element rootNode = document.getRootElement();
		List<Element> list = rootNode.getChildren("configuration");
		for (int i = 0; i < list.size(); i++) {
			Element node = (Element) list.get(i);
			connectionUrl = node.getChildText("CONNECTION_URL");
			driver = node.getChildText("DRIVER");
			dbUsername = node.getChildText("DB_USERNAME");
			dbPassword = node.getChildText("DB_PASSWORD");
		}

		Class.forName(driver);

		conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
		return true;
	}

}
