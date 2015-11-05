package net.atos.si.ma.mt.astreinte.service.impl;

import java.util.List;

import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.astreinte.service.UtilisateurService;
import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.service.impl.GenericServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl extends
		GenericServiceImpl<Utilisateur, GenericDAO<Utilisateur, Long>>
		implements UtilisateurService {

	@Autowired
	@Qualifier("utilisateurDAO")
	@Override
	public void setDao(GenericDAO<Utilisateur, Long> dao) {
		super.setDao(dao);
	}

	@Override
	public Utilisateur checkLogin(String login, String password) {
		List<Utilisateur> utilisateurs = getDao().listByCriteres(
				"from Utilisateur where login='" + login.toLowerCase() + "'", null, null);
		if (utilisateurs == null || utilisateurs.size() != 1)
			return null;
		else {
			Utilisateur utilisateur = utilisateurs.get(0);
			if (password.equals(utilisateur.getPassword()))
				return utilisateur;
			else
				return null;
		}
	}

	@Override
	public Utilisateur find(long id) {
		return getDao().load(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		List<Utilisateur> utilisateurs = getDao().listByCriteres(
				"from Utilisateur where login='" + username + "'", null, null);
		if (utilisateurs == null || utilisateurs.size() != 1)
			return null;
		else {
			Utilisateur utilisateur = utilisateurs.get(0);

			return utilisateur;
		}

	}

	@Override
	public Utilisateur getUserByDas(String das) {
		List<Utilisateur> utilisateurs = getDao().listByCriteres(
				"from Utilisateur where das='" + das + "'", null, null);
		if (utilisateurs == null || utilisateurs.size() != 1)
			return null;
		else {
			Utilisateur utilisateur = utilisateurs.get(0);

			return utilisateur;
		}
	}
}
