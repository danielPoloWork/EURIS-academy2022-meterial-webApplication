package it.euris.libreria;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import it.euris.libreria.data.model.Autori;
import it.euris.libreria.service.AutoriService;

@SpringBootApplication
public class LibreriaApplication {

	public static void main(String[] args) {

		// SpringApplication.run(LibreriaApplication.class, args);
		SpringApplication app = new SpringApplication(LibreriaApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		ConfigurableApplicationContext context = app.run(args);
		System.out.println("Hello world!");
		
		AutoriService autoriService = context.getBean(AutoriService.class);
		
		Autori a = autoriService.getByIdWithEntityManager(1L);
		System.out.println("Autore2 trovato: " + a.getId() + " - " + a.getNome() + " " + a.getCognome());
		
		List<Autori> autoriList = autoriService.getAutoriByNomeAndCognome("Alessandro", "Manzoni");
		for(Autori autore : autoriList) {
			System.out.println("Autore trovato: " + autore.getId() + " - " + autore.getNome() + " " + autore.getCognome());
		}
		
//		LibriService libriService = new LibriService();
//		long l = autoriService.save(Autori.builder().nome("Joanne K.").cognome("Rowling").build());
//		Autori autore = autoriService.getById(l);
//		
//		libriService.save(Libri.builder().titolo("Harry Potter").autore(autore).isbn("XXX").build());
//		
//		List<Libri> libriList = libriService.getLibriByTitoloOrIsbn("Nuovo titolo", "XXX");
//		for (Libri libro : libriList) {
//			System.out.println("Libro trovato: " + libro.getId() + " - " + libro.getTitolo() + " - " + libro.getIsbn());
//		}
//
//		List<Libri> libriList = libriService.getLibriByTitoloAndAutore("promessi", "Alessandro", "Rowling");
//		for (Libri libro : libriList) {
//			System.out.println("Libro trovato: " + libro.getId() + " - " + libro.getTitolo() + " - " + libro.getAutore().getNome()+ " - " + libro.getAutore().getCognome());
//		}
//
//		long idAutoreInserito = autoriService.save(Autori.builder().nome("Alessandro2").cognome("Davide2").build());
//		
//		Autori autori = autoriService.getById(idAutoreInserito);
//		System.out.println(autori.getId() + " - " + autori.getNome() + " " + autori.getCognome());
//		
//		
//		
//		LibriService libriService = new LibriService();
//		Libri libro = libriService.getById(1L);
//		System.out.println("Nome libro: " + libro.getTitolo());
//		System.out.println("Nome autore: " + libro.getAutore().getId() + " - " + libro.getAutore().getNome() + " " + libro.getAutore().getCognome());
//
//		Autori autore2L = autoriService.getById(2L);
//		
//		libriService.save(Libri.builder().titolo("Nuovo titolo").autore(autore2L).build());
//		
//		Autori autore1L = autoriService.getById(1L);
//		System.out.println("Nome autore: " + autore1L.getId() + " - " + autore1L.getNome() + " " + autore1L.getCognome());
//		for (Libri libroTrovato : autore1L.getLibri()) {
//			System.out.println("Nome libro trovato: " + libroTrovato.getTitolo());
//		}
//		
//		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);
//
//		Database db = new Database();
//		boolean dbConnesso = false;
//		try {
//			dbConnesso = db.connetti();
//		} catch (FileNotFoundException e) {
//			System.out.println("File config non trovato");
//			e.printStackTrace();
//		} catch (IOException | JDOMException e) {
//			System.out.println("Problema nella lettura del file config");
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.out.println("Errore durante la connesione al database");
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			System.out.println("Driver per la connesione al database non trovato");
//			e.printStackTrace();
//		}
//		
//		if (dbConnesso) {
//			
//			try {
//				AutoriDto.insert(db.getConn(), Autori.builder().nome("Alessandro").cognome("Manzoni").build());
//				Autori autori = AutoriDto.selectByNomeCognome(db.getConn(), "Alessandro", "Manzoni");
//				LibriDto.insert(db.getConn(), Libri.builder().titolo("I promessi sposi").idAutore(autori.getId()).build());
//			} catch (SQLException e) {
//				System.out.println("Errore durante la scrittura dei dati");
//				e.printStackTrace();
//			}
//		}
//		
//		if (dbConnesso) {
//			try {
//				ResultSet rs = LibriDto.selectById(db.getConn(), 1L);
//				while (rs.next()) {
//					System.out.println("Nuovo libro trovato");
//					System.out.println(rs.getString("titolo"));
//					System.out.println(rs.getString("idautore"));
//				}
//			} catch (SQLException e) {
//				System.out.println("Errore durante la lettura dei dati");
//				e.printStackTrace();
//			}
//		}

	}

}
