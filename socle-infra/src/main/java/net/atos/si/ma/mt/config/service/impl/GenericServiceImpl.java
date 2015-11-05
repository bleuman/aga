package net.atos.si.ma.mt.config.service.impl;

import java.util.List;

import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.service.GenericService;

import org.springframework.stereotype.Service;

@Service
public class GenericServiceImpl<T, DAO extends GenericDAO<T,Long>> implements
		GenericService<T,Long> {

	private DAO dao;

	public void setDao(DAO dao) {
		this.dao = dao;
	}

	public DAO getDao() {
		return dao;
	}

	public T save(T entity) {
		return dao.save(entity);

	}

	public T update(T entity) {
		return dao.save(entity);

	}

	public void delete(T entity) {
		dao.delete(entity);

	}

	public List<T> listAll() {
		List<T> list = dao.getAll();
		return list;
	}

	public List<T> listByCriteres(String query, String[] keys, Object[] values) {
		List<T> list = dao.listByCriteres(query, keys,values);
		return list;
	}

	@Override
	public T oneByCriteres(String query, String[] keys, Object[] values) {
		return dao.oneByCriteres(query, keys, values);
	}

	@Override
	public List<Object> listByQuery(String query) {
		return dao.listByQuery(query);
	}

	@Override
	public Object oneByQuery(String query) {
		return dao.oneByQuery(query);
	}

	@Override
	public List<Object> sqlListByQuery(String query) {
		return dao.sqlListByQuery(query);
	}

	@Override
	public Object sqlOneByQuery(String query) {
		return dao.sqlOneByQuery(query);
	}

	@Override
	public T load(Long id) {
		// TODO Auto-generated method stub
		return dao.load(id);
	}

}
