package it.euris.libreria.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
import org.springframework.stereotype.Component;

import it.euris.libreria.data.model.Autori;

@Component
public class AutoriService {
	
	@PersistenceContext
	private EntityManager entityManager;
		
	public Autori getById(Long id) {
		
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		
		Autori autore = (Autori) session.get(Autori.class, id);
		
		factory.close();
		session.close();
		
		return autore;
	}

	public Long save(Autori autore) {
		
		// HibernateUtil
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
				
		Transaction t = session.beginTransaction();

		Long id = (Long) session.save(autore);
		t.commit();
		factory.close();
		session.close();
		
		return id;
	}
	
	public List<Autori> getAutoriByNomeAndCognome(String nome, String cognome) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Autori> query = cb.createQuery(Autori.class);
		Root<Autori> rootAutori = query.from(Autori.class);
		
		Path<String> pathName = rootAutori.get("nome");
		Path<String> pathCognome = rootAutori.get("cognome");
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.like(pathName, nome));
		predicates.add(cb.like(pathCognome, cognome));
		
		// WHERE name='Alessandro' AND cognome='Manzoni' 
		query.select(rootAutori).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return entityManager.createQuery(query).getResultList();
	}

}
