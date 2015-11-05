package net.atos.si.ma.mt.astreinte.service.impl;

import net.atos.si.ma.mt.astreinte.model.TypeAstreinte;
import net.atos.si.ma.mt.astreinte.service.AstreinteService;
import net.atos.si.ma.mt.astreinte.service.EtatAstreinteService;

public class AstreinteUtils{

	
	
	public static AstreinteService astreinteService;
	
	public static EtatAstreinteService etatAstreinteService;

	public static AstreinteService getAstreinteService() {
		return astreinteService;
	}

	public static void setAstreinteService(AstreinteService astreinteService) {
		AstreinteUtils.astreinteService = astreinteService;
	}

	public static EtatAstreinteService getEtatAstreinteService() {
		return etatAstreinteService;
	}

	public static void setEtatAstreinteService(
			EtatAstreinteService etatAstreinteService) {
		AstreinteUtils.etatAstreinteService = etatAstreinteService;
	}

	
	
	


	
	
}
