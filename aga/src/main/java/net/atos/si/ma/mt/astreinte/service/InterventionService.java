package net.atos.si.ma.mt.astreinte.service;

import java.util.Date;
import java.util.List;

import net.atos.si.ma.mt.astreinte.model.Intervention;
import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.config.service.GenericService;
import net.atos.si.ma.mt.config.web.ValidationException;

public interface InterventionService extends GenericService<Intervention,Long> {
	
	boolean cheakIntervention(Intervention intervention,Utilisateur utilisateur,Long IdAstreinte) throws ValidationException;
	//"from Intervention where 	utilisateur.id=:ressourceid  and 	( dateD BETWEEN :hdebut and :hfin or  dateF BETWEEN :hdebut and :hfin  or  :hdebut BETWEEN dateD and dateF or  :hfin BETWEEN dateD and dateF ) order by dateD")
	List<Intervention> checkChevauchement(Integer idRessource,Date dateD ,Date dateF, Long idAstreinte,Long idIntervention);
		
	List<Intervention> getInterventionsForRessourcesByAstreinte(Integer idRessource,Long idAstreinte);

	
}
