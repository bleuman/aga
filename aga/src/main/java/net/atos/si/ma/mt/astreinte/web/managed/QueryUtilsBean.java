package net.atos.si.ma.mt.astreinte.web.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.atos.si.ma.mt.astreinte.model.Entite;
import net.atos.si.ma.mt.config.dao.GenericDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.faces.security.FaceletsAuthorizeTag;
import org.springframework.stereotype.Component;

@Component("mbean")
@Scope("application")
public class QueryUtilsBean implements Map<String, Object> {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("parameterDAO")
	private GenericDAO<Entite, Long> parameterDAO;

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object get(Object key) {
		log.trace("call mbean get with query : "+key);
		if (!key.toString().startsWith("sql:"))
			return parameterDAO.listByQuery(key.toString());
		else
			return parameterDAO.sqlListByQuery(key.toString().replace("sql:",
					""));
	}

	@Override
	public Object put(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean areAllGranted(String authorities) throws IOException {
		FaceletsAuthorizeTag authorizeTag = new FaceletsAuthorizeTag();
		authorizeTag.setIfAllGranted(authorities);
		return authorizeTag.authorizeUsingGrantedAuthorities();
	}

	/**
	 * Returns true if the user has any of the given authorities.
	 * 
	 * @param authorities
	 *            a comma-separated list of user authorities.
	 */
	public boolean areAnyGranted(String authorities) throws IOException {
		FaceletsAuthorizeTag authorizeTag = new FaceletsAuthorizeTag();
		authorizeTag.setIfAnyGranted(authorities);
		return authorizeTag.authorizeUsingGrantedAuthorities();
	}

	/**
	 * Returns true if the user does not have any of the given authorities.
	 * 
	 * @param authorities
	 *            a comma-separated list of user authorities.
	 */
	public boolean areNotGranted(String authorities) throws IOException {
		FaceletsAuthorizeTag authorizeTag = new FaceletsAuthorizeTag();
		authorizeTag.setIfNotGranted(authorities);
		return authorizeTag.authorizeUsingGrantedAuthorities();
	}

	/**
	 * Returns true if the user is allowed to access the given URL and HTTP
	 * method combination. The HTTP method is optional and case insensitive.
	 */
	public boolean isAllowed(String url, String method) throws IOException {
		FaceletsAuthorizeTag authorizeTag = new FaceletsAuthorizeTag();
		authorizeTag.setUrl(url);
		authorizeTag.setMethod(method);
		return authorizeTag.authorizeUsingUrlCheck();
	}

	public Collection<String> toStringArray(String value, String sep) {
		Collection<String> ret = new ArrayList<String>();
		for (String string : value.split(sep)) {
			ret.add(string);
		}
		return ret;
	}

	public Date getMaxDate(Date date) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			return calendar.getTime();
		} else
			return null;
	}

	
}
