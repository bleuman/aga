package net.atos.si.ma.mt.astreinte.service;

import net.atos.si.ma.mt.astreinte.model.Utilisateur;

public interface ExchangeAuthenticatorService {

	boolean isUserAuthenthicated(Utilisateur utilisateur);

}
