package it.euris.libreria.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import it.euris.libreria.data.model.Autori;
import it.euris.libreria.data.model.Libri;

public class LibriService {

	public Libri getById(Long id) {

		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();

		Libri libro = (Libri) session.get(Libri.class, id);

		factory.close();
		session.close();

		return libro;
	}

	public Long save(Libri libro) {

		// HibernateUtil
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();

		Transaction t = session.beginTransaction();

		Long id = (Long) session.save(libro);
		t.commit();
		factory.close();
		session.close();

		return id;
	}
	
	public List<Libri> getLibriByTitoloOrIsbn(String titolo, String isbn) {
		
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Libri> query = cb.createQuery(Libri.class);
		Root<Libri> rootLibri = query.from(Libri.class);
		
		Path<String> pathTitolo = rootLibri.get("titolo");
		Path<String> pathIsbn = rootLibri.get("isbn");
		
		Predicate predicateTitolo = cb.equal(pathTitolo, titolo);
		Predicate predicateIsbn = cb.equal(pathIsbn, isbn);
		Predicate predicate = cb.or(predicateTitolo, predicateIsbn);
		
		query.select(rootLibri).where(predicate);
		
		return session.createQuery(query).getResultList();
	}
	
	public List<Libri> getLibriByTitoloAndAutore(String titolo, String nome, String cognome) {
		
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Libri> query = cb.createQuery(Libri.class);
		Root<Libri> rootLibri = query.from(Libri.class);
		Join<Libri, Autori> joinAutori = rootLibri.join(Libri.FK_COLUMN, JoinType.INNER);
		
		Path<String> pathTitolo = rootLibri.get("titolo");
		Path<String> pathName = joinAutori.get("nome");
		Path<String> pathCognome = joinAutori.get("cognome");
		
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.like(pathTitolo, titolo));
		predicates.add(cb.like(pathName, nome));
		predicates.add(cb.like(pathCognome, cognome));
		
		// SELECT * FROM libri  INNER JOIN autori  ON libri.idautore=autori.id WHERE titolo ='Promessi' OR name='Alessandro' OR cognome='Rowling' 
		query.select(rootLibri).where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		
		return session.createQuery(query).getResultList();
	}

}
