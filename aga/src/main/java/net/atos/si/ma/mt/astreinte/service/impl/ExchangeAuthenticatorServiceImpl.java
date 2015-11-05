package net.atos.si.ma.mt.astreinte.service.impl;

import java.util.List;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.astreinte.service.ExchangeAuthenticatorService;
import net.atos.si.ma.mt.config.dao.GenericDAO;
import net.atos.si.ma.mt.config.service.impl.GenericServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ExchangeAuthenticatorServiceImpl extends
GenericServiceImpl<Utilisateur, GenericDAO<Utilisateur, Long>> implements
		ExchangeAuthenticatorService, UserDetailsService {

	@Autowired
	@Qualifier("utilisateurDAO")
	@Override
	public void setDao(GenericDAO<Utilisateur, Long> dao) {
		super.setDao(dao);
	}
	
	@Autowired
	private ExchangeService exchangeAuthenticator;



	@Override
	public boolean isUserAuthenthicated(Utilisateur utilisateur) {
		ExchangeCredentials credentials = new WebCredentials(
				utilisateur.getLogin(), utilisateur.getPassword(), "ww930");
		exchangeAuthenticator.setCredentials(credentials);
		try {

			exchangeAuthenticator.getInboxRules();
			exchangeAuthenticator.setCredentials(null);
			return true;
		} catch (Exception e) {
			exchangeAuthenticator.setCredentials(null);
			return false;
		}

	}

	@Override
	public Utilisateur loadUserByUsername(String das) {
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
