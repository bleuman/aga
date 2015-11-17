package net.atos.si.ma.mt.config.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.atos.si.ma.mt.config.dao.GenericDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("unchecked")
@Transactional
public class GenericDaoImpl<T, PK extends Serializable> implements
		GenericDAO<T, PK> {
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	private final Logger log = LoggerFactory.getLogger(getClass());

	public static final String PERSISTENCE_UNIT_NAME = "ApplicationEntityManager";

	/**
	 * Entity manager, injected by Spring using @PersistenceContext annotation
	 * on setEntityManager()
	 */
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;
	private Class<T> persistentClass;

	/**
	 * Constructor that takes in a class to see which type of entity to persist.
	 * Use this constructor when subclassing or using dependency injection.
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 */
	public GenericDaoImpl(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public GenericDaoImpl() {

	}

	/**
	 * Constructor that takes in a class to see which type of entity to persist.
	 * Use this constructor when subclassing or using dependency injection.
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 * @param entityManager
	 *            the configured EntityManager for JPA implementation.
	 */
	public GenericDaoImpl(final Class<T> persistentClass,
			EntityManager entityManager) {
		this.persistentClass = persistentClass;
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return this.entityManager.createQuery(
				"select obj from " + this.persistentClass.getName() + " obj")
				.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAllDistinct() {
		Collection<T> result = new LinkedHashSet<T>(getAll());
		return new ArrayList<T>(result);
	}

	/**
	 * {@inheritDoc}
	 */
	public T load(PK id) {
		log.trace("trying to load: " + persistentClass.getName() + ", id:" + id);
		T entity = this.entityManager.find(this.persistentClass, id);

		if (entity == null) {
			String msg = this.persistentClass + "' object with id '" + id
					+ "' not found...";
			log.warn(msg);
			// throw new EntityNotFoundException(msg);
			return null;
		}

		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(PK id) {
		T entity = this.entityManager.find(this.persistentClass, id);
		return entity != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public T save(T object) {
		log.trace("trying to save: " + object.toString());
		return this.entityManager.merge(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(T object) {
		entityManager.remove(entityManager.contains(object) ? object
				: entityManager.merge(object));
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(PK id) {
		log.trace("trying to remove: " + persistentClass.getName() + ", id:"
				+ id);
		this.entityManager.remove(this.load(id));
	}

	public List<T> listByCriteres(String query, String[] keys, Object[] values) {
		Query hquery = this.entityManager.createQuery(query);
		if (keys != null && values != null && keys.length == values.length) {
			int count = keys.length;
			for (int i = 0; i < count; i++) {
				Object value = values[i];
				String rawkey = keys[i];
				String key = rawkey.substring(rawkey.indexOf(':') + 1,
						rawkey.length());
				String parmaName = rawkey.substring(0, rawkey.indexOf(':'));
				switch (key) {
				case "string":
					hquery.setParameter(parmaName, (String) value);
					break;

				case "integer":
					hquery.setParameter(parmaName, (Integer) value);
					break;

				case "date":
					hquery.setParameter(parmaName, (Date) value);
					break;

				case "time":
					hquery.setParameter(parmaName, (Date) value);
					break;

				default:
					break;
				}
			}
		}
		return (List<T>) hquery.getResultList();
	}

	public T oneByCriteres(String query, String[] keys, Object[] values) {
		List<T> list = listByCriteres(query, keys, values);
		if (list == null || list.isEmpty())
			return null;
		else
			return (T) list.get(0);
	}

	@Override
	public List<Object> listByQuery(String query) {
		Query hquery = this.entityManager.createQuery(query);
		return hquery.getResultList();
	}

	@Override
	public Object oneByQuery(String query) {
		Query hquery = this.entityManager.createQuery(query);
		return hquery.getSingleResult();
	}

	@Override
	public List<Object> sqlListByQuery(String query) {
		Query hquery = this.entityManager.createNativeQuery(query);
		return hquery.getResultList();
	}

	@Override
	public Object sqlOneByQuery(String query) {
		Query hquery = this.entityManager.createNativeQuery(query);
		return hquery.getSingleResult();
	}

	@Override
	public List<Object> executeNamedQuery(String query,
			Map<String, Object> params) {
		Query  nqQuery= this.entityManager.createNamedQuery(query);
		
		for (String key : params.keySet()) {
			nqQuery.setParameter(key, params.get(key));
		}
		return nqQuery.getResultList();
	}

}
