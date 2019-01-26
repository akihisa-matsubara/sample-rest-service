package jp.co.jaxrs.sample.common.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDao<T> {
	@PersistenceContext(unitName = "mydb")
	EntityManager entityManager;

	public void create(T entity) {
		entityManager.persist(entity);
	}

	public void update(T entity) {
		entityManager.merge(entity);
	}

	public void delete(T entity) {
		entityManager.remove(entity);
	}
}
