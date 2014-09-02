package com.employees.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class BaseIT {

	protected static EntityManager entityManager;

	@AfterClass
	public static void end() throws Exception {
		entityManager.close();
	}

	@BeforeClass
	public static void start() throws Exception {
		entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
	}

	@Before
	public void setUp() throws Exception {
		createEntities();
	}

	@After
	public void tearDown() throws Exception {

		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}

		entityManager.clear();

		removeEntities();

	}

	protected void beginTransaction() {
		entityManager.getTransaction().begin();
	}

	protected void commitTransaction() {
		entityManager.getTransaction().commit();
	}

	protected abstract void createEntities();

	protected <W> W getFirstEntityOfType(Class<W> type) {
		List<W> list = entityManager.createQuery("from " + type.getName(), type).getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	protected <W> W getLastEntityOfType(Class<W> type) {
		List<W> list = entityManager.createQuery("from " + type.getName(), type).getResultList();
		return list.isEmpty() ? null : list.get(list.size() - 1);
	}

	protected void insert(Object entity) {
		beginTransaction();
		entityManager.persist(entity);
		commitTransaction();
	}

	protected void insertAll(List<?> entities) {
		for (Object entity : entities) {
			insert(entity);
		}
	}

	protected <W> List<W> query(String query, Class<W> type) {
		return entityManager.createQuery(query, type).getResultList();
	}

	protected void remove(Object entity) {
		beginTransaction();
		entityManager.remove(entity);
		commitTransaction();
	}

	protected void removeAll(List<Object> entities) {
		for (Object entity : entities) {
			remove(entity);
		}
	}

	@SuppressWarnings("unchecked")
	protected <W> void removeAllOfType(Class<W> type) {
		removeAll((List<Object>) query("from " + type.getName(), type));
	}

	protected void removeEntities() {
		beginTransaction();
		entityManager.createNativeQuery("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK ").executeUpdate();
		commitTransaction();
	}

	protected void update(Object entity) {
		beginTransaction();
		entityManager.merge(entity);
		commitTransaction();
	}

}
