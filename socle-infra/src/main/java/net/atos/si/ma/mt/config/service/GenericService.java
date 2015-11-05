package net.atos.si.ma.mt.config.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor=Exception.class)
public interface GenericService<T,PK> {
	T save(T entity); 
	T update (T entity); 
	T load(PK id);
	void delete (T entity); 
	List<T> listAll();
	List<T> listByCriteres(String query, String[] keys, Object[] values);
	T oneByCriteres(String query, String[] keys, Object[] values);
	List<Object> listByQuery(String query);

	Object oneByQuery(String query);

	List<Object> sqlListByQuery(String query);

	Object sqlOneByQuery(String query);
	
	
}
