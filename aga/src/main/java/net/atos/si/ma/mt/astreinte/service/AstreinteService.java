package net.atos.si.ma.mt.astreinte.service;

import java.util.Date;
import java.util.List;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.config.service.GenericService;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AstreinteService extends GenericService<Astreinte,Long> {
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	Astreinte findByRef(String ref, String domaine);
	
	List<Astreinte> checkChevauchementRessources(List<String> listRessources,Date debut,Date fin);

	
}
