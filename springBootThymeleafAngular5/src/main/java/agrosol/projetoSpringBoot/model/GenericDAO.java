package agrosol.projetoSpringBoot.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

public class GenericDAO<T> {
	protected static EntityManager em = ConnectionFactory.getEntityManager();

	public static <T> void save(T entity) {
		em.getTransaction().begin(); 
		em.persist(entity);
		em.getTransaction().commit();
	}
	
	public static <T> void update(T entity) {
		em.getTransaction().begin(); 
		em.merge(entity);
		em.getTransaction().commit();
	}
	
	public static <T> void delete(T entity) {
		em.getTransaction().begin(); 		
		em.remove(em.contains(entity) ? entity : em.merge(entity));
		em.getTransaction().commit();
	}
	
	public static <T> List<T> findAll(Class<T> persistedClass) {		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(persistedClass);
		query.from(persistedClass);      
		return em.createQuery(query).getResultList();
	} 
	
	public static <T> T findById(Class<T> persistedClass, Long fieldValue) {
		Session session = em.unwrap(Session.class);
		return session.get(persistedClass, fieldValue);
	}  
		
	public static <T> List<T> findByField(Class<T> persistedClass, String fieldName, String fieldValue) {
		return findByField(persistedClass, fieldName, fieldValue, false);
	}   	
	
	public static <T> List<T> findByField(Class<T> persistedClass, String fieldName, String fieldValue, boolean exact) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(persistedClass);   
		Root<T> root = criteria.from(persistedClass);
		criteria.distinct(true);
		if(exact) {
			criteria.where(builder.like(root.get(fieldName), fieldValue));
		} else {
			criteria.where(builder.like(root.get(fieldName),"%"+fieldValue+"%"));
		}
		return em.createQuery(criteria).getResultList();
	}
}

