package net.atos.si.ma.mt.config.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface GenericUtilisateurService extends
		UserDetailsService {
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	UserDetails find(long id);

	UserDetails checkLogin(String login, String password);

	UserDetails getUserByDas(String das);

}
